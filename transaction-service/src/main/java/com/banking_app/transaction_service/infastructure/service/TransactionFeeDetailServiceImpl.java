package com.banking_app.transaction_service.infastructure.service;

import com.banking_app.transaction_service.application.service.TransactionFeeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionFeeDetailServiceImpl implements TransactionFeeDetailService {
  private final TransactionFeeDetailService transactionFeeDetailService;
}
