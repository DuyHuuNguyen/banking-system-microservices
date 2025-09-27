package com.banking_app.auth_service.application.service;

import com.example.dto.UpdatingAccountMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

public interface ConsumerUpdatingAccountService {
  void updateAccount(
      UpdatingAccountMessage updatingAccountMessage, Message message, Channel channel);
}
