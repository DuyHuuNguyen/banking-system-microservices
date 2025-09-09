package com.banking_app.transaction_service.infastructure.service;

import com.banking_app.transaction_service.application.service.TransactionFeeDetailService;
import com.banking_app.transaction_service.domain.repository.TransactionFeeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionFeeDetailServiceImpl implements TransactionFeeDetailService {
  private final TransactionFeeDetailRepository transactionFeeDetailRepository;
}
