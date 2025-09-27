package com.banking_app.user_service.application.service;

import com.example.dto.AccountWithRoleDTO;
import reactor.core.publisher.Mono;

public interface AuthService {

  Mono<AccountWithRoleDTO> getAccountWithRoleFromAccessToken(String accessToken);
}
