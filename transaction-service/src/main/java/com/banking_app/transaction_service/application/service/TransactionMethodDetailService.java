package com.banking_app.transaction_service.application.service;

import com.banking_app.transaction_service.domain.entity.transaction_method_detail.TransactionMethodDetail;
import reactor.core.publisher.Mono;

public interface TransactionMethodDetailService {
  Mono<TransactionMethodDetail> save(TransactionMethodDetail transactionMethodDetail);

  Mono<TransactionMethodDetail> findById(Long id);
}
