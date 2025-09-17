package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.domain.entity.account.Account;
import com.banking_app.auth_service.domain.repository.AccountRepository;
import com.banking_app.auth_service.infrastructure.until.AccountSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Account> findByPersonalIdentificationNumber(String personalIdentificationNumber) {
    return this.accountRepository.findAccountByPersonalIdentificationNumber(
        personalIdentificationNumber);
  }

  @Override
  public Mono<Account> findById(Long id) {
    return this.accountRepository.findById(id);
  }

  @Override
  public void updateFirstLoginAndOneDeviceById(Long id, Boolean isFirstLogin, Boolean isOneDevice) {
    this.accountRepository.updateFirstLoginAndOneDeviceById(id, isFirstLogin, isOneDevice);
  }

  @Override
  public Mono<Void> updateFirstLoginAndOneDeviceByPersonalId(
      String personalId, Boolean isFirstLogin, Boolean isOneDevice) {
    return this.accountRepository.updateFirstLoginAndOneDeviceByPersonalIdentificationNumber(
        personalId, isFirstLogin, isOneDevice);
  }

  @Override
  public Mono<Account> save(Account account) {
    return this.accountRepository.save(account);
  }

  @Override
  public Flux<Account> findAll(AccountSpecification accountSpecification) {
    return this.r2dbcEntityTemplate.select(accountSpecification.getQuery(), Account.class);
  }
}
