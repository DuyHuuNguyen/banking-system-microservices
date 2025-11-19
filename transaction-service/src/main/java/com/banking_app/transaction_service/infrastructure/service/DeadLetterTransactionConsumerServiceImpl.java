package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import com.banking_app.transaction_service.application.service.DeadLetterTransactionConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeadLetterTransactionConsumerServiceImpl
    implements DeadLetterTransactionConsumerService {

  @Override
  @KafkaListener(topics = "${kafka-transaction.dead-letter-transaction-topic}")
  public Mono<Void> handleFailTransaction(CreateTransactionDTO createTransactionDTO) {
    return null;
  }
}
