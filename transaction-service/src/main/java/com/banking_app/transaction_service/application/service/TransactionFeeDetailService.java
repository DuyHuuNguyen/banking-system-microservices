package com.banking_app.transaction_service.application.service;

import com.banking_app.transaction_service.domain.entity.transaction_fee_detail.TransactionFeeDetail;
import reactor.core.publisher.Mono;

public interface TransactionFeeDetailService {
  Mono<TransactionFeeDetail> save(TransactionFeeDetail transactionFeeDetail);

  Mono<TransactionFeeDetail> findById(Long id);
}
