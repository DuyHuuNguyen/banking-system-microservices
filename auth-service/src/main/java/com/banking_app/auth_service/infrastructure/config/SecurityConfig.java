package com.banking_app.auth_service.infrastructure.config;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.JwtService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.account.Account;
import com.banking_app.auth_service.infrastructure.rest.interceptor.ExampleHandlerFilterFunction;
import com.banking_app.auth_service.infrastructure.security.SecurityReactiveUserDetailsService;
import com.banking_app.auth_service.infrastructure.security.SecurityUserDetails;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Log4j2
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final SecurityReactiveUserDetailsService securityReactiveUserDetailsService;

  private final JwtService jwtService;
  private final AccountService accountService;
  private final RoleService roleService;

  private static final String[] WHITE_LISTS = {
    "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**", "/api/v1/auths/login"
  };

  private final List<String> PUBLIC_APIS = List.of("/actuator/**", "/api/v1/auths/login");
  private final List<String> SWAGGER_URLS = List.of("/swagger-ui/**", "/v3/api-docs/**");


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
    UserDetailsRepositoryReactiveAuthenticationManager reactiveAuthenticationManager =
        new UserDetailsRepositoryReactiveAuthenticationManager(securityReactiveUserDetailsService);
    reactiveAuthenticationManager.setPasswordEncoder(passwordEncoder);
    return reactiveAuthenticationManager;
  }


  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http, ReactiveAuthenticationManager reactiveAuthenticationManager) {
    http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(ServerHttpSecurity.CorsSpec::disable)
//        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .httpBasic(httpBasicSpec ->
                    httpBasicSpec.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
            )
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

        .authorizeExchange(
            exchanges ->
                exchanges.pathMatchers(WHITE_LISTS).permitAll().anyExchange().authenticated())
        .authenticationManager(reactiveAuthenticationManager)


            .addFilterAt((exchange, chain) -> {
              String path = exchange.getRequest().getURI().getPath();
              log.info("path : {}", path);
              var isSwagger = SWAGGER_URLS.stream().anyMatch(path::startsWith);
              var isPublic = PUBLIC_APIS.stream().anyMatch(path::startsWith);

              if (isSwagger || isPublic) return chain.filter(exchange);
              String personalIdentifyInformation = "1234";

              return this.accountService
                      .findByPersonalIdentificationNumber(personalIdentifyInformation)
                      .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
//                      .map(this::buildSecurityUserDetails)
                      .flatMap(account -> {
                          log.info(" load role for account");
                          return this.roleService
                                  .findRolesByAccountId(account.getId())
                                  .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
                                  .map(role -> new SimpleGrantedAuthority(role.getRoleName().getContent()))
                                  .collectList()
                                  .map(simpleGrantedAuthority -> SecurityUserDetails.build(account, simpleGrantedAuthority));
                      })
                      .flatMap(
                              securityUserDetails -> {
                                log.info("created user details {}",securityUserDetails.getAuthorities());
                                log.info("created user details {}",securityUserDetails.getEmail());
                                  Authentication authentication = new UsernamePasswordAuthenticationToken(
                                          securityUserDetails,
                                          null,
                                          securityUserDetails.getAuthorities()
                                  );

                                return chain.filter(exchange)
                                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

                              });
            },SecurityWebFiltersOrder.HTTP_BASIC);
    return http.build();

  }
}
