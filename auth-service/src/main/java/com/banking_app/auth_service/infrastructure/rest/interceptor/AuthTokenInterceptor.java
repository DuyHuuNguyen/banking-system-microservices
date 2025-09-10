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
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Log4j2
//@Component
//@Order(Integer.MIN_VALUE)
@RequiredArgsConstructor
public class AuthTokenInterceptor implements WebFilter {
  private final JwtService jwtService;
  private final AccountService accountService;
  private final RoleService roleService;
  private final List<String> PUBLIC_APIS = List.of("/actuator/**", "/api/v1/auths/login");
  private final List<String> SWAGGER_URLS = List.of("/swagger-ui/**", "/v3/api-docs/**");

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    String path = exchange.getRequest().getURI().getPath();
    log.info("path : {}", path);
    var isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
    var isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);

    if (isSwagger || isPublic) return chain.filter(exchange);

    String token = this.getTokenFromHeader(exchange);
    var isValidateToken = true||
            this.jwtService.validateToken(token) || true;

    if (isValidateToken) {
      String personalIdentifyInformation = "1234";
//          this.jwtService.getPersonalIdentificationNumberFromJwtToken(token);
      return this.accountService
          .findByPersonalIdentificationNumber(personalIdentifyInformation)
          .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
          .map(this::buildSecurityUserDetails)
          .flatMap(
              securityUserDetails -> {
                log.info("created userdatails");
                return chain
                        .filter(exchange)
                        .contextWrite(
                                ctx -> ctx.put(SecurityUserDetails.class, securityUserDetails));

              });
    }

    return chain.filter(exchange);
  }

  private Mono<UserDetails> buildSecurityUserDetails(Account account) {
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
    log.info("token : {}", authHeader);
    return authHeader;
  }
}
