package com.banking_app.transaction_service.application.service;

import com.banking_app.transaction_service.domain.entity.transaction_fee.TransactionFee;
import reactor.core.publisher.Mono;

public interface TransactionFeeService {
  Mono<TransactionFee> save(TransactionFee transactionFee);

  Mono<TransactionFee> findById(Long id);
}
