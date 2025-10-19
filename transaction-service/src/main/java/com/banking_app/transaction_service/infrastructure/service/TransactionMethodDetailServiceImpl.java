package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.TransactionMethodDetailService;
import com.banking_app.transaction_service.domain.entity.transaction_method_detail.TransactionMethodDetail;
import com.banking_app.transaction_service.domain.repository.TransactionMethodDetailRepository;
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
public class TransactionMethodDetailServiceImpl implements TransactionMethodDetailService {
  private final TransactionMethodDetailRepository transactionMethodDetailRepository;
  private final ReactiveRedisTemplate<String, TransactionMethodDetail> reactiveRedisTemplate;

  private final String TRANSACTION_FEE_DETAIL_KEY = "TRANSACTION_FEE_DETAIL_KEY_%s";
  private final Duration timeoutOfTransactionFeeDetailInCache = Duration.ofSeconds(604800);

  @Override
  public Mono<TransactionMethodDetail> save(TransactionMethodDetail transactionMethodDetail) {
    boolean isTheFirstTimeOfSaveTransactionMethodDetail = transactionMethodDetail.getId() == null;
    if (!isTheFirstTimeOfSaveTransactionMethodDetail) transactionMethodDetail.reUpdate();
    return this.transactionMethodDetailRepository
        .save(transactionMethodDetail)
        .switchIfEmpty(Mono.error(new PermissionDeniedException(ErrorCode.SAVE_ENTITY_ERROR)))
        .flatMap(
            transactionMethodDetailStored -> {
              String transactionKey =
                  String.format(
                      this.TRANSACTION_FEE_DETAIL_KEY, transactionMethodDetailStored.getId());
              return this.reactiveRedisTemplate
                  .opsForValue()
                  .set(
                      transactionKey,
                      transactionMethodDetailStored,
                      timeoutOfTransactionFeeDetailInCache)
                  .switchIfEmpty(Mono.error(new CacheException(ErrorCode.CAN_NOT_CACHE)))
                  .thenReturn(transactionMethodDetailStored);
            });
  }

  @Override
  public Mono<TransactionMethodDetail> findById(Long id) {
    String transactionFeeKey = String.format(TRANSACTION_FEE_DETAIL_KEY, id);
    return this.reactiveRedisTemplate
        .opsForValue()
        .get(transactionFeeKey)
        .cast(TransactionMethodDetail.class)
        .switchIfEmpty(
            this.transactionMethodDetailRepository
                .findById(id)
                .switchIfEmpty(
                    Mono.error(new EntityNotFoundException(ErrorCode.TRANSACTION_FEE_NOT_FOUND)))
                .flatMap(
                    transactionMethodDetail ->
                        this.reactiveRedisTemplate
                            .opsForValue()
                            .set(
                                transactionFeeKey,
                                transactionMethodDetail,
                                timeoutOfTransactionFeeDetailInCache)
                            .thenReturn(transactionMethodDetail)));
  }
}
