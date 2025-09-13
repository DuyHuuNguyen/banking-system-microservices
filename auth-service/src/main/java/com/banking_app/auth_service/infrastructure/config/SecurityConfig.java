package com.banking_app.auth_service.infrastructure.config;

import com.banking_app.auth_service.infrastructure.config.filter_factory_method.FilterCreator;
import com.banking_app.auth_service.infrastructure.security.SecurityReactiveUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Log4j2
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final SecurityReactiveUserDetailsService securityReactiveUserDetailsService;
  private final FilterCreator filterCreator;

  private static final String[] WHITE_LISTS = {
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/actuator/**",
    "/api/v1/auths/login",
    "/api/v1/auths/refresh-token"
  };

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
    http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .httpBasic(
            httpBasicSpec ->
                httpBasicSpec.authenticationEntryPoint(
                    new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .authorizeExchange(
            exchanges ->
                exchanges.pathMatchers(WHITE_LISTS).permitAll().anyExchange().authenticated())
        .addFilterBefore(this.filterCreator.create(), SecurityWebFiltersOrder.HTTP_BASIC)
        .authenticationManager(reactiveAuthenticationManager);
    return http.build();
  }
}
