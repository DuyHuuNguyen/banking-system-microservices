package com.banking_app.auth_service.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Auth Service API",
            version = "1.0.0",
            description = "API documentation for Auth Service"),
    servers = {@Server(url = "/"), @Server(url = "/auth-service")})
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    paramName = "Authorization")
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi apiGroup() {
    return GroupedOpenApi.builder()
        .group("Auth-Service")
        .packagesToScan("com.banking_app.auth_service.infrastructure.rest.controller")
        .build();
  }
}
