package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.dto.MailOTP;
import com.banking_app.auth_service.application.service.ProducerEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerMailServiceImpl implements ProducerEmailService {

  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchangeName}")
  private String EXCHANGE_NAME;

  @Value("${rabbitmq.user-mail-routing-key}")
  private String ROUTING_KEY;

  @Override
  public void send(MailOTP mailOTP) {
    this.rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, mailOTP);
  }
}
