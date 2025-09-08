package com.banking_app.transaction_service.domain.repository;

import com.banking_app.transaction_service.domain.entity.transaction.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {}
