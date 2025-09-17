package com.banking_app.auth_service.application.service;

import com.banking_app.auth_service.domain.entity.account.Account;
import com.banking_app.auth_service.infrastructure.until.AccountSpecification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
  Mono<Account> findByPersonalIdentificationNumber(String personalIdentificationNumber);

  Mono<Account> findById(Long id);

  void updateFirstLoginAndOneDeviceById(Long id, Boolean isFirstLogin, Boolean isOneDevice);

  Mono<Void> updateFirstLoginAndOneDeviceByPersonalId(
      String personalId, Boolean isFirstLogin, Boolean isOneDevice);

  Mono<Account> save(Account account);

  Flux<Account> findAll(AccountSpecification accountSpecification);
}
