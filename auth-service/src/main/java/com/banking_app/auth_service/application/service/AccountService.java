package com.banking_app.auth_service.application.service;

import com.banking_app.auth_service.domain.entity.account.Account;
import reactor.core.publisher.Mono;

public interface AccountService {
  Mono<Account> findByPersonalIdentificationNumber(String personalIdentificationNumber);

  Mono<Account> findById(Long id);

  void updateFirstLoginAndOneDeviceById(Long id, Boolean isFirstLogin, Boolean isOneDevice);

  void updateFirstLoginAndOneDeviceByPersonalId(
      String personalId, Boolean isFirstLogin, Boolean isOneDevice);

  void save(Account account);
}
