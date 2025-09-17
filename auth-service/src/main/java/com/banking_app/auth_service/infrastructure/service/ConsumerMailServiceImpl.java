package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.dto.MailOTP;
import com.banking_app.auth_service.application.service.ConsumerEmailService;
import com.banking_app.auth_service.application.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConsumerMailServiceImpl implements ConsumerEmailService {
  private final MailService mailService;

  @Override
  @RabbitListener(queues = "${rabbitmq.user-mail-queue}")
  public void receive(MailOTP mailOTP) {
    log.info("receive message 😊");
    this.mailService.sendOtp(mailOTP);
  }
}
