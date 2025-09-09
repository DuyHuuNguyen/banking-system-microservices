package com.banking_app.auth_service.infrastructure.config;

import com.banking_app.auth_service.infrastructure.security.SecurityReactiveUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Log4j2
@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final SecurityReactiveUserDetailsService securityReactiveUserDetailsService;

  private static final String[] WHITE_LISTS = {
    "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**"
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
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .authorizeExchange(
            exchanges ->
                exchanges.pathMatchers(WHITE_LISTS).permitAll().anyExchange().authenticated())
        .authenticationManager(reactiveAuthenticationManager)
        .build();
  }
}
