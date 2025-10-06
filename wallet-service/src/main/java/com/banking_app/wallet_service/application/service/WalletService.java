package com.banking_app.wallet_service.application.service;

import com.banking_app.wallet_service.domain.entity.wallet.Wallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletService {
  Mono<Wallet> save(Wallet wallet);

  Mono<Wallet> findById(Long id);

  Flux<Wallet> findByUserId(Long userId);
}
