package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.TransactionService;
import com.banking_app.transaction_service.domain.entity.transaction.Transaction;
import com.banking_app.transaction_service.domain.repository.TransactionRepository;
import com.example.enums.ErrorCode;
import com.example.exception.CacheException;
import com.example.exception.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;

  private final String TRANSACTION_KEY = "TRANSACTION_KEY_%s";
  private final Duration timeoutOfTransactionInCache = Duration.ofSeconds(604800);

  private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

  @Override
  public Mono<Transaction> save(Transaction transaction) {
    boolean isTheFirstSaving = transaction.getId() == null;
    if (!isTheFirstSaving) transaction.reUpdate();
    return transactionRepository
        .save(transaction)
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.SAVE_ENTITY_ERROR)))
        .flatMap(
            transactionStored -> {
              String transactionKey =
                  String.format(this.TRANSACTION_KEY, transactionStored.getId());
              return this.reactiveRedisTemplate
                  .opsForValue()
                  .set(transactionKey, transactionStored, timeoutOfTransactionInCache)
                  .switchIfEmpty(Mono.error(new CacheException(ErrorCode.ACCOUNT_NOT_FOUND)))
                  .thenReturn(transactionStored);
            });
  }

  @Override
  public Mono<Transaction> findById(Long id) {
    String transactionKey = String.format(this.TRANSACTION_KEY, id);

    return this.reactiveRedisTemplate
        .opsForValue()
        .get(transactionKey)
        .switchIfEmpty(
            this.transactionRepository
                .findById(id)
                .switchIfEmpty(
                    Mono.error(new EntityNotFoundException(ErrorCode.TRANSACTION_NOT_FOUND)))
                .flatMap(
                    transaction ->
                        this.reactiveRedisTemplate
                            .opsForValue()
                            .set(transactionKey, transaction, timeoutOfTransactionInCache)
                            .thenReturn(transaction)))
        .cast(Transaction.class);
  }
}
