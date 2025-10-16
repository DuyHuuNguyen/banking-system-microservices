package com.banking_app.wallet_service.application.service;

import com.banking_app.wallet_service.domain.entity.fund.Fund;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FundService {
  Flux<Fund> findByWalletId(Long walletId);

  Mono<Fund> save(Fund fund);

  Mono<Fund> findById(Long id);

  Mono<Fund> findByUserIdAndFundId(Long userId, Long fundId);
}
