package com.banking_app.wallet_service.domain.repository;

import com.banking_app.wallet_service.domain.entity.fund.Fund;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends ReactiveCrudRepository<Fund, Long> {}
