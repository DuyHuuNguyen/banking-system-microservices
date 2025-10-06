package com.banking_app.wallet_service.application.service;

import com.banking_app.wallet_service.domain.entity.wallet_detail.WalletDetail;
import reactor.core.publisher.Mono;

public interface WalletDetailService {
  Mono<WalletDetail> save(WalletDetail walletDetail);

  Mono<WalletDetail> findByWalletId(Long walletId);

  Mono<WalletDetail> findById(Long id);
}
