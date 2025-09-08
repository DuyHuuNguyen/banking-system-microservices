package com.banking_app.transaction_service.domain.repository;

import com.banking_app.transaction_service.domain.entity.transaction_fee_detail.TransactionFeeDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFeeDetailRepository
    extends ReactiveCrudRepository<TransactionFeeDetail, Long> {}
