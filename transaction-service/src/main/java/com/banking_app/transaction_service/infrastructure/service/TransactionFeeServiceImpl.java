package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.TransactionFeeService;
import com.banking_app.transaction_service.domain.entity.transaction_fee.TransactionFee;
import com.banking_app.transaction_service.domain.repository.TransactionFeeRepository;
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
public class TransactionFeeServiceImpl implements TransactionFeeService {
  private final TransactionFeeRepository transactionFeeRepository;

  private final String TRANSACTION_FEE_KEY = "TRANSACTION_FEE_KEY_%s";
  private final Duration timeoutOfTransactionInCache = Duration.ofSeconds(604800);

  private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

  @Override
  public Mono<TransactionFee> save(TransactionFee transactionFee) {
    var isTheFirstTimeOfSaveTransactionFee = transactionFee.getId() == null;
    if (!isTheFirstTimeOfSaveTransactionFee) transactionFee.reUpdate();
    return this.transactionFeeRepository
        .save(transactionFee)
        .switchIfEmpty(Mono.error(new PermissionDeniedException(ErrorCode.SAVE_ENTITY_ERROR)))
        .flatMap(
            transactionFeeStored -> {
              String transactionKey =
                  String.format(this.TRANSACTION_FEE_KEY, transactionFeeStored.getId());
              return this.reactiveRedisTemplate
                  .opsForValue()
                  .set(transactionKey, transactionFeeStored, timeoutOfTransactionInCache)
                  .switchIfEmpty(Mono.error(new CacheException(ErrorCode.ACCOUNT_NOT_FOUND)))
                  .thenReturn(transactionFeeStored);
            });
  }

  @Override
  public Mono<TransactionFee> findById(Long id) {
    String transactionFeeKey = String.format(TRANSACTION_FEE_KEY, id);
    return this.reactiveRedisTemplate
        .opsForValue()
        .get(transactionFeeKey)
        .cast(TransactionFee.class)
        .switchIfEmpty(
            this.transactionFeeRepository
                .findById(id)
                .switchIfEmpty(
                    Mono.error(new EntityNotFoundException(ErrorCode.TRANSACTION_FEE_NOT_FOUND)))
                .flatMap(
                    transactionFee ->
                        this.reactiveRedisTemplate
                            .opsForValue()
                            .set(transactionFeeKey, transactionFee, timeoutOfTransactionInCache)
                            .thenReturn(transactionFee)));
  }
}
