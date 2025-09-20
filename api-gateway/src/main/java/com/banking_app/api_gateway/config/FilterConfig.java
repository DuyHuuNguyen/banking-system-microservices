package com.banking_app.api_gateway.config;

import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class FilterConfig implements GlobalFilter {
  private static final Logger log = LogManager.getLogger(FilterConfig.class);

  private static final List<String> SWAGGER_URLS = List.of("/swagger-ui/", "/v3/api-docs");
  private static final List<String> PUBLIC_APIS =
      List.of(
          "/api/v1/auths/login",
          "/api/v1/auths/refresh-token",
          "/api/v1/auths/forgot-password",
          "/api/v1/auths/sign-up");

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var request = exchange.getRequest();
    HttpHeaders headers = request.getHeaders();

    var path = String.valueOf(request.getPath());
    var port = Objects.requireNonNull(request.getLocalAddress()).getPort();

    var isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
    var isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);
    log.info("swagger {} | public {} | {} {}", isSwagger, isPublic, port, path);

    if (isSwagger || isPublic) return chain.filter(exchange);
    List<String> authenticationHeaders = headers.get(HttpHeaders.AUTHORIZATION);

    var isMissingAcceptToken =
        authenticationHeaders != null && !authenticationHeaders.get(0).startsWith("Bearer ");

    if (isMissingAcceptToken) {
      log.info("Request {} is missing token in header", path);
      return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
          .then(exchange.getResponse().setComplete());
    }

    return chain.filter(exchange);
  }
}
