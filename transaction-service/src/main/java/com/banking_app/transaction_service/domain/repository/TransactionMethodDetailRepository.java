package com.banking_app.transaction_service.domain.repository;

import com.banking_app.transaction_service.domain.entity.transaction_method_detail.TransactionMethodDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMethodDetailRepository
    extends ReactiveCrudRepository<TransactionMethodDetail, Long> {}
