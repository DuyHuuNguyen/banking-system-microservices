package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.ProducerMessageUpdateAccountService;
import com.example.dto.UpdatingAccountMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProducerUpdateAccountServiceImpl implements ProducerMessageUpdateAccountService {
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange-updating-account}")
  private String exchangeUpdateAccount;

  @Value("${rabbitmq.routing-key-update-account}")
  private String routingKeyUpdateAccount;

  @Value("${rabbitmq.queue-update-account}")
  private String updateAccountQueue;

  @Override
  public void updateInfoAccount(UpdatingAccountMessage updatingAccountMessage) {
    log.info("updating account by userId = {}", updatingAccountMessage.getUserId());
    this.rabbitTemplate.convertAndSend(
        exchangeUpdateAccount, routingKeyUpdateAccount, updatingAccountMessage);
  }
}
