package com.banking_app.auth_service.infrastructure.rest.interceptor;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.JwtService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.account.Account;
import com.banking_app.auth_service.infrastructure.security.SecurityUserDetails;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
public class AuthTokenInterceptor implements WebFilter {
  private final JwtService jwtService;
  private final AccountService accountService;
  private final RoleService roleService;

  private final List<String> PUBLIC_APIS =
      List.of(
          "/actuator/",
          "/api/v1/auths/login",
          "/api/v1/auths/refresh-token",
          "/api/v1/auths/internal/");
  private final List<String> SWAGGER_URLS =
      List.of("/swagger-ui/", "/swagger-ui/index.html", "/v3/api-docs/", "/favicon.ico");

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    if (isSkipAuthentication(exchange)) return chain.filter(exchange);

    Boolean isValidateToken = false;

    String token = this.getTokenFromHeader(exchange);
    try {
      isValidateToken = this.jwtService.validateToken(token);
    } catch (Exception e) {
      log.info("exception validate jwt {}", e.getCause());
      return this.setResponseUnAuthenticated(exchange);
    }
    log.info("isValidateToken {}", isValidateToken);
    if (isValidateToken) {
      String personalIdentifyInformation =
          this.jwtService.getPersonalIdentificationNumberFromJwtToken(token);
      log.info("personalIdentifyInformation : {}", personalIdentifyInformation);

      return this.accountService
          .findByPersonalIdentificationNumber(personalIdentifyInformation)
          .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
          .flatMap(this::buildSecurityUserDetails)
          .flatMap(
              securityUserDetails ->
                  this.addAuthenticationIntoContext(exchange, chain, securityUserDetails));
    }

    return this.setResponseUnAuthenticated(exchange);
  }

  private boolean isSkipAuthentication(ServerWebExchange exchange) {
    String path = exchange.getRequest().getURI().getPath();
    log.info("path {}", path);
    boolean isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
    boolean isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);
    log.info("isSkipAuth {}", isSwagger || isPublic);
    return isSwagger || isPublic;
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

  private Mono<Void> addAuthenticationIntoContext(
      ServerWebExchange exchange, WebFilterChain chain, SecurityUserDetails userDetails) {
    return chain
        .filter(exchange)
        .contextWrite(
            ReactiveSecurityContextHolder.withAuthentication(
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities())));
  }

  private Mono<SecurityUserDetails> buildSecurityUserDetails(Account account) {
    return this.roleService
        .findRolesByAccountId(account.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
        .map(role -> new SimpleGrantedAuthority(role.getRoleName().getContent()))
        .collectList()
        .map(simpleGrantedAuthority -> SecurityUserDetails.build(account, simpleGrantedAuthority));
  }

  private String getTokenFromHeader(ServerWebExchange serverWebExchange) {
    HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
    String authHeader = headers.getFirst("Authorization");
    if (authHeader == null) return null;
    log.info("token : {}", authHeader);
    return authHeader.substring(7);
  }
}
