package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import com.banking_app.transaction_service.application.service.HandleTransactionProducerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HandleTransactionProducerServiceImpl implements HandleTransactionProducerService {

  private final KafkaTemplate<String, CreateTransactionDTO> kafkaTemplate;

  @Value("${kafka-transaction.handle-transaction-topic}")
  private String handleTransactionTopic;

  @Override
  public Mono<Void> pushIntoHandleCreateTransactionTopic(
      CreateTransactionDTO createTransactionDTO) {
    return Mono.fromRunnable(
        () -> kafkaTemplate.send(handleTransactionTopic, createTransactionDTO));
  }

  @PostConstruct
  void hehe() {
    this.pushIntoHandleCreateTransactionTopic(
            CreateTransactionDTO.builder().transactionBalance(199.0).build())
        .subscribe();
  }
}
