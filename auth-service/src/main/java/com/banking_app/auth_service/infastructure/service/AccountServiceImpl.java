package com.banking_app.auth_service.infastructure.service;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
}
