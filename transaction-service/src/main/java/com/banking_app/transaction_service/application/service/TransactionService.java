package com.banking_app.transaction_service.application.service;

import com.banking_app.transaction_service.domain.entity.transaction.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionService {
  Mono<Transaction> save(Transaction transaction);

  Mono<Transaction> findById(Long id);
}
