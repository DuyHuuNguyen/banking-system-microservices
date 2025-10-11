package com.banking_app.wallet_service.infrastructure.service;

import com.banking_app.wallet_service.application.service.WalletService;
import com.banking_app.wallet_service.domain.entity.wallet.Wallet;
import com.banking_app.wallet_service.domain.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
  private final WalletRepository walletRepository;
  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Wallet> save(Wallet wallet) {
    if (wallet.getId() != null) wallet.reUpdate();
    return this.walletRepository.save(wallet);
  }

  @Override
  public Mono<Wallet> findById(Long id) {
    return this.walletRepository.findById(id);
  }

  @Override
  public Flux<Wallet> findByUserId(Long userId) {
    return this.walletRepository.findAllByUserId(userId);
  }
}
