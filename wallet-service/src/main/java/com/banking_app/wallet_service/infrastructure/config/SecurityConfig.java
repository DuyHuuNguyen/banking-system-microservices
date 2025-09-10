package com.banking_app.wallet_service.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

  private static final String[] WHITE_LISTS = {
    "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @PostConstruct
  public void init() {
    log.info("run security");
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .authorizeExchange(
            exchanges ->
                exchanges.pathMatchers(WHITE_LISTS).permitAll().anyExchange().authenticated())
        .build();
  }
}
