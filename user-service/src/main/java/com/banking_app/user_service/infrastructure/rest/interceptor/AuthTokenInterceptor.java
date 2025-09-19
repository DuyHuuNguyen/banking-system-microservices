package com.banking_app.user_service.infrastructure.rest.interceptor;

import com.banking_app.user_service.application.service.AuthService;
import com.banking_app.user_service.infrastructure.security.SecurityUserDetails;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.example.dto.AccountWithRoleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Log4j2
@RequiredArgsConstructor
public class AuthTokenInterceptor implements WebFilter {

  private final AuthService authService;

  private final int TIMEOUT_RANGE = 3;

  private final List<String> PUBLIC_APIS = List.of("/actuator/","/api/v1/users/hehe");
  private final List<String> SWAGGER_URLS =
      List.of("/swagger-ui/", "/swagger-ui/index.html", "/v3/api-docs", "/favicon.ico");

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    if (isSkipAuthentication(exchange))
      return chain
          .filter(exchange)
          .timeout(Duration.ofSeconds(this.TIMEOUT_RANGE))
          .onErrorResume(
              TimeoutException.class,
              ex -> {
                exchange.getResponse().setStatusCode(HttpStatus.REQUEST_TIMEOUT);
                return exchange.getResponse().setComplete();
              });


    String token = this.getTokenFromHeader(exchange);
    log.info("token: {}", token);

    var isNullAccessToken = (token == null);
    if(isNullAccessToken)  return this.setResponseUnAuthenticated(exchange);

    return this.authService.getAccountWithRoleFromAccessToken(token)
            .doOnNext(accountWithRoleDTO -> log.info(accountWithRoleDTO.toString()))
            .flatMap(accountWithRoleDTO ->
                accountWithRoleDTO.getIsEnabled() ? Mono.just(accountWithRoleDTO): this.responseUnauthorized(exchange))
            .cast(AccountWithRoleDTO.class)
            .map(SecurityUserDetails::build)
            .flatMap(
                    securityUserDetails -> this.addAuthenticationIntoContext(exchange, chain, securityUserDetails));


  }


  private Mono<Void> setResponseUnAuthenticated(ServerWebExchange exchange) {
    return exchange
            .getResponse()
            .setComplete()
            .then(
                    Mono.defer(
                            () -> {
                              exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                              return exchange.getResponse().setComplete();
                            }));
  }

  private Mono<Void> responseUnauthorized(ServerWebExchange exchange){
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      String body = """
            {
              "status": 401,
              "error": "Unauthorized",
              "message": "Token is invalided"
            }
            """;

      byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
      return exchange.getResponse().writeWith(Mono.just(buffer));
  }

  private boolean isSkipAuthentication(ServerWebExchange exchange) {
    String path = exchange.getRequest().getURI().getPath();
    log.info("path {}", path);
    boolean isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
    boolean isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);
    log.info("isSkipAuth {}", isSwagger || isPublic);
    return isSwagger || isPublic;
  }

  private Mono<Void> addAuthenticationIntoContext(
      ServerWebExchange exchange, WebFilterChain chain, SecurityUserDetails userDetails) {
    return chain
        .filter(exchange)
        .timeout(Duration.ofSeconds(this.TIMEOUT_RANGE))
        .onErrorResume(
            TimeoutException.class,
            ex -> {
              exchange.getResponse().setStatusCode(HttpStatus.REQUEST_TIMEOUT);
              return exchange.getResponse().setComplete();
            })
        .contextWrite(
            ReactiveSecurityContextHolder.withAuthentication(
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities())));
  }

  private String getTokenFromHeader(ServerWebExchange serverWebExchange) {
    HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
    String authHeader = headers.getFirst("Authorization");
    if (authHeader == null) return null;
    log.info("token : {}", authHeader);
    return authHeader.substring(7);
  }
}
