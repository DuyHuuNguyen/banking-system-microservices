package com.banking_app.auth_service.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.exchangeName}")
  private String exchange;

  @Value("${rabbitmq.user-mail-queue}")
  private String userMailQueue;

  @Value("${rabbitmq.user-mail-routing-key}")
  private String userMailRoutingKey;

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Queue userMailQueue() {
    return new Queue(userMailQueue, false);
  }

  @Bean
  public Jackson2JsonMessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Binding userMailBinding() {
    return BindingBuilder.bind(userMailQueue()).to(exchange()).with(userMailRoutingKey);
  }

  @Value("${rabbitmq.exchange-updating-account}")
  private String exchangeUpdateAccount;

  @Value("${rabbitmq.routing-key-update-account}")
  private String routingKeyUpdateAccount;

  @Value("${rabbitmq.queue-update-account}")
  private String updateAccountQueue;

  @Bean
  public TopicExchange exchangeUpdateAccount() {
    return new TopicExchange(exchangeUpdateAccount);
  }

  @Bean
  public Queue updateAcountQueue() {
    return new Queue(updateAccountQueue);
  }

  @Bean
  public Binding udpateAcountBinding() {
    return BindingBuilder.bind(this.updateAcountQueue())
        .to(this.exchangeUpdateAccount())
        .with(this.routingKeyUpdateAccount);
  }
}
