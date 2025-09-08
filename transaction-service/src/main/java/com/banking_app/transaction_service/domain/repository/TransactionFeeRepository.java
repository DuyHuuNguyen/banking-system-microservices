package com.banking_app.transaction_service.domain.repository;

import com.banking_app.transaction_service.domain.entity.transaction_fee.TransactionFee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFeeRepository extends ReactiveCrudRepository<TransactionFee, Long> {}
