package com.banking_app.transaction_service.application.service;

import com.banking_app.transaction_service.domain.entity.transaction_method.TransactionMethod;
import reactor.core.publisher.Mono;

public interface TransactionMethodService {
  Mono<TransactionMethod> save(TransactionMethod transactionMethod);

  Mono<TransactionMethod> findById(Long id);
}
