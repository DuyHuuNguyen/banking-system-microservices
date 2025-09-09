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
}
