package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.TransactionMethodService;
import com.banking_app.transaction_service.domain.entity.transaction_method.TransactionMethod;
import com.banking_app.transaction_service.domain.repository.TransactionMethodRepository;
import com.example.enums.ErrorCode;
import com.example.exception.CacheException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionMethodServiceImpl implements TransactionMethodService {
  private final TransactionMethodRepository transactionMethodRepository;
  private final ReactiveRedisTemplate<String, TransactionMethod> reactiveRedisTemplate;

  private final String TRANSACTION_METHOD_KEY = "TRANSACTION_METHOD_KEY_%s";
  private final Duration timeoutOfTransactionInCache = Duration.ofSeconds(604800);

  @Override
  public Mono<TransactionMethod> save(TransactionMethod transactionMethod) {
    var isTheFirstTimeOfSaveTransactionFee = transactionMethod.getId() == null;
    if (!isTheFirstTimeOfSaveTransactionFee) transactionMethod.reUpdate();
    return this.transactionMethodRepository
        .save(transactionMethod)
        .switchIfEmpty(Mono.error(new PermissionDeniedException(ErrorCode.SAVE_ENTITY_ERROR)))
        .flatMap(
            transactionMethodStored -> {
              String transactionKey =
                  String.format(this.TRANSACTION_METHOD_KEY, transactionMethodStored.getId());
              return this.reactiveRedisTemplate
                  .opsForValue()
                  .set(transactionKey, transactionMethodStored, timeoutOfTransactionInCache)
                  .switchIfEmpty(Mono.error(new CacheException(ErrorCode.CAN_NOT_CACHE)))
                  .thenReturn(transactionMethodStored);
            });
  }

  @Override
  public Mono<TransactionMethod> findById(Long id) {
    String transactionMethodKey = String.format(TRANSACTION_METHOD_KEY, id);
    return this.reactiveRedisTemplate
        .opsForValue()
        .get(transactionMethodKey)
        .cast(TransactionMethod.class)
        .switchIfEmpty(
            this.transactionMethodRepository
                .findById(id)
                .switchIfEmpty(
                    Mono.error(new EntityNotFoundException(ErrorCode.TRANSACTION_METHOD_FOUND)))
                .flatMap(
                    transactionFee ->
                        this.reactiveRedisTemplate
                            .opsForValue()
                            .set(transactionMethodKey, transactionFee, timeoutOfTransactionInCache)
                            .thenReturn(transactionFee)));
  }
}
