package com.banking_app.transaction_service.infastructure.service;

import com.banking_app.transaction_service.application.service.TransactionFeeService;
import com.banking_app.transaction_service.domain.repository.TransactionFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionFeeServiceImpl implements TransactionFeeService {
  private final TransactionFeeRepository transactionFeeRepository;
}
