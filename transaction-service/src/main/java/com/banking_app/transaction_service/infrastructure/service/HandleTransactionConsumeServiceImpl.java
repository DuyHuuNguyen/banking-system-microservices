package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.dto.CreateTransactionDTO;
import com.banking_app.transaction_service.application.service.HandleTransactionConsumerService;
import com.banking_app.transaction_service.application.service.WalletGrpcClientService;
import com.example.base.OtpTransactionRequest;
import com.example.base.OtpTransactionResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HandleTransactionConsumeServiceImpl implements HandleTransactionConsumerService {
  private final KafkaTemplate<String, CreateTransactionDTO> kafkaTemplate;

  private final WalletGrpcClientService walletGrpcClientService;

  @Value("${kafka-transaction.retry-transaction-topic}")
  private String retryTransactionTopic;

  @Value("${kafka-transaction.dead-letter-transaction-topic}")
  private String deadLetterTransactionTopic;

  private Integer maxRetryTimes = 3;

  @Override
  @KafkaListener(topics = "${kafka-transaction.handle-transaction-topic}")
  public Mono<Void> consumeCreateTransaction(CreateTransactionDTO createTransactionDTO) {
    return this.findOtpTransaction(createTransactionDTO.getUserIdOfOriginator())
        .flatMap(
            otpTransactionResponse -> {
              boolean isValidOtpTransaction =
                  createTransactionDTO.getOpt().equals(otpTransactionResponse.getOtp());
              if (isValidOtpTransaction) {
                // create transaction and fee
                if (createTransactionDTO.getIsWallet()) {
                  return this.walletGrpcClientService
                      .findWalletById(createTransactionDTO.getOriginatorInformationId())
                      .switchIfEmpty(
                          Mono.defer(
                              () -> {
                                boolean isOverRetryTimes =
                                    createTransactionDTO.getRetryTimes() > this.maxRetryTimes;
                                if (isOverRetryTimes)
                                  this.kafkaTemplate.send(
                                      this.deadLetterTransactionTopic, createTransactionDTO);
                                else {
                                  createTransactionDTO.plusRetryTimes();
                                  this.kafkaTemplate.send(
                                      this.retryTransactionTopic, createTransactionDTO);
                                }
                                return Mono.empty();
                              }))
                      .flatMap(
                          walletResponse -> {
                            boolean isValidBalance =
                                walletResponse.getBalance()
                                        - createTransactionDTO.getTransactionBalance()
                                    >= 0;
                            if (isValidBalance) {
                              // push message sub balance wallet
                              // push message plus balance b waller
                              // create record transaction
                            }

                            return null;
                          });
                }

              } else {
                kafkaTemplate.send(deadLetterTransactionTopic, createTransactionDTO);
              }

              return Mono.empty();
            });
  }

  private Mono<OtpTransactionResponse> findOtpTransaction(Long userId) {
    return WebClient.create("http://localhost:8082")
        .post()
        .uri("/api/auths/internal/otp")
        .header("secret-api-key", "auth-23130075")
        .bodyValue(OtpTransactionRequest.builder().userId(userId).build())
        .retrieve()
        .bodyToMono(OtpTransactionResponse.class)
        .timeout(Duration.ofSeconds(3))
        .onErrorReturn(OtpTransactionResponse.builder().build());
  }
}
