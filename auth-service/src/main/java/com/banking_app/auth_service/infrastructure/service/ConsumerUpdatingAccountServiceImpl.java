package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.ConsumerUpdatingAccountService;
import com.example.dto.UpdatingAccountMessage;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConsumerUpdatingAccountServiceImpl implements ConsumerUpdatingAccountService {
  private final AccountService accountService;

  @Override
  @RabbitListener(queues = "${rabbitmq.queue-update-account}")
  @SneakyThrows
  public void updateAccount(
      UpdatingAccountMessage updatingAccountMessage, Message message, Channel channel) {
    log.info("ok updated account");
    this.accountService
        .findByUserId(updatingAccountMessage.getUserId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
        .flatMap(
            account -> {
              account.changeEmail(updatingAccountMessage.getEmail());
              account.changePersonalId(updatingAccountMessage.getPersonalId());
              log.info("update {}", updatingAccountMessage);
              return this.accountService.save(account);
            })
        .subscribe();
  }
}
