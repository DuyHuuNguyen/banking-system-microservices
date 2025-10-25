package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import com.banking_app.transaction_service.application.service.HandleTransactionConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HandleTransactionConsumeServiceImpl implements HandleTransactionConsumerService {

  @Override
  @KafkaListener(topics = "${kafka-transaction.handle-transaction-topic}")
  public Mono<Void> consumeCreateTransaction(CreateTransactionDTO createTransactionDTO) {
    return Mono.fromRunnable(
        () -> {
          System.out.println("Handling transaction: " + createTransactionDTO);
        });
  }
}
