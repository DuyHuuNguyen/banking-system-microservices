package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.domain.entity.account.Account;
import com.banking_app.auth_service.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;

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
  public void updateFirstLoginAndOneDeviceByPersonalId(
      String personalId, Boolean isFirstLogin, Boolean isOneDevice) {
    this.accountRepository.updateFirstLoginAndOneDeviceByPersonalIdentificationNumber(
        personalId, isFirstLogin, isOneDevice);
  }

  @Override
  public void save(Account account) {
    this.accountRepository.save(account);
  }
}
