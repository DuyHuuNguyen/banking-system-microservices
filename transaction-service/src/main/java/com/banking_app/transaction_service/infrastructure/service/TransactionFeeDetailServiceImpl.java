package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.TransactionFeeDetailService;
import com.banking_app.transaction_service.domain.entity.transaction_fee_detail.TransactionFeeDetail;
import com.banking_app.transaction_service.domain.repository.TransactionFeeDetailRepository;
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
public class TransactionFeeDetailServiceImpl implements TransactionFeeDetailService {
  private final TransactionFeeDetailRepository transactionFeeDetailRepository;

  private final ReactiveRedisTemplate<String, TransactionFeeDetail>
      transactionFeeDetailReactiveRedisTemplate;

  private final String TRANSACTION_FEE_DETAIL_KEY = "TRANSACTION_FEE_DETAIL_KEY_%s";
  private final Duration timeoutOfTransactionFeeDetailInCache = Duration.ofSeconds(604800);

  @Override
  public Mono<TransactionFeeDetail> save(TransactionFeeDetail transactionFeeDetail) {
    var isTheFirstTimeOfSaveTransactionFeeDetail = transactionFeeDetail.getId() == null;
    if (!isTheFirstTimeOfSaveTransactionFeeDetail) transactionFeeDetail.reUpdate();
    return this.transactionFeeDetailRepository
        .save(transactionFeeDetail)
        .switchIfEmpty(Mono.error(new PermissionDeniedException(ErrorCode.SAVE_ENTITY_ERROR)))
        .flatMap(
            transactionFeeStored -> {
              String transactionKey =
                  String.format(this.TRANSACTION_FEE_DETAIL_KEY, transactionFeeStored.getId());
              return this.transactionFeeDetailReactiveRedisTemplate
                  .opsForValue()
                  .set(transactionKey, transactionFeeStored, timeoutOfTransactionFeeDetailInCache)
                  .switchIfEmpty(Mono.error(new CacheException(ErrorCode.CAN_NOT_CACHE)))
                  .thenReturn(transactionFeeStored);
            });
  }

  @Override
  public Mono<TransactionFeeDetail> findById(Long id) {
    String transactionFeeKey = String.format(TRANSACTION_FEE_DETAIL_KEY, id);
    return this.transactionFeeDetailReactiveRedisTemplate
        .opsForValue()
        .get(transactionFeeKey)
        .cast(TransactionFeeDetail.class)
        .switchIfEmpty(
            this.transactionFeeDetailRepository
                .findById(id)
                .switchIfEmpty(
                    Mono.error(new EntityNotFoundException(ErrorCode.TRANSACTION_FEE_NOT_FOUND)))
                .flatMap(
                    transactionFeeDetail ->
                        this.transactionFeeDetailReactiveRedisTemplate
                            .opsForValue()
                            .set(
                                transactionFeeKey,
                                transactionFeeDetail,
                                timeoutOfTransactionFeeDetailInCache)
                            .thenReturn(transactionFeeDetail)));
  }
}
