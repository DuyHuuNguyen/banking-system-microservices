package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.AuthService;
import com.example.dto.AccountWithRoleDTO;
import com.example.enums.ErrorCode;
import com.example.exception.UnauthorizedException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final String BAS_URL = "http://localhost:8082";

  @Override
  public Mono<AccountWithRoleDTO> getAccountWithRoleFromAccessToken(String accessToken) {
    return WebClient.create("http://localhost:8082")
        .post()
        .uri("/api/v1/auths/internal/parse-token")
        .header("accessToken", accessToken)
        .header("secret-api-key", "auth-access-token-23130075")
        .retrieve()
        .bodyToMono(AccountWithRoleDTO.class)
        .timeout(Duration.ofSeconds(3))
        .onErrorReturn(AccountWithRoleDTO.builder().build())
        .switchIfEmpty(Mono.error(new UnauthorizedException(ErrorCode.JWT_INVALID)));
  }
}
