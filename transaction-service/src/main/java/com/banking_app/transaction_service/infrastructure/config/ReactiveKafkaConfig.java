package com.banking_app.transaction_service.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.*;

@Configuration
public class ReactiveKafkaConfig {

  @Value("${kafka-transaction.handle-transaction-topic}")
  private String handleTransactionsTopic;

  @Value("${kafka-transaction.retry-transaction-topic}")
  private String retrieveTransactionsTopic;

  @Value("${kafka-transaction.dead-letter-transaction-topic}")
  private String deadLetterTransactionTopic;

  @Bean
  public NewTopic handleTransactionsTopic() {
    return TopicBuilder.name(handleTransactionsTopic).build();
  }

  @Bean
  public NewTopic retrieveTransactionsTopic() {
    return TopicBuilder.name(retrieveTransactionsTopic).build();
  }

  @Bean
  public NewTopic deadLetterTransactionTopic() {
    return TopicBuilder.name(deadLetterTransactionTopic).build();
  }
}
