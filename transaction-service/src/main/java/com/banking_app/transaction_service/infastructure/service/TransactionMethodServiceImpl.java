package com.banking_app.transaction_service.infastructure.service;

import com.banking_app.transaction_service.application.service.TransactionMethodService;
import com.banking_app.transaction_service.domain.repository.TransactionMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionMethodServiceImpl implements TransactionMethodService {
  private final TransactionMethodRepository transactionMethodRepository;
}
