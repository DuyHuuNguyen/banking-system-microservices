package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import com.banking_app.transaction_service.application.service.RetryTransactionConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReTryTransactionConsumerServiceImpl implements RetryTransactionConsumerService {

  @Override
  @KafkaListener(topics = "${kafka-transaction.retry-transaction-topic}")
  public Mono<Void> retryTransaction(CreateTransactionDTO createTransactionDTO) {
    return null;
  }
}
