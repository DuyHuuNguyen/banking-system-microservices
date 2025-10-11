package com.banking_app.wallet_service.domain.repository;

import com.banking_app.wallet_service.domain.entity.fund.Fund;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FundRepository extends ReactiveCrudRepository<Fund, Long> {
  Flux<Fund> findByWalletId(Long walletId);
}
