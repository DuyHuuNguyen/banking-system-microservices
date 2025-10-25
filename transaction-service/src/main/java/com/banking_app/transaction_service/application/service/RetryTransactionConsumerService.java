package com.banking_app.transaction_service.application.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import reactor.core.publisher.Mono;

public interface RetryTransactionConsumerService {
  Mono<Void> retryTransaction(CreateTransactionDTO createTransactionDTO);
}
