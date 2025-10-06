package com.banking_app.wallet_service.infrastructure.service;

import com.banking_app.wallet_service.application.service.WalletDetailService;
import com.banking_app.wallet_service.domain.entity.wallet_detail.WalletDetail;
import com.banking_app.wallet_service.domain.repository.WalletDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WalletDetailServiceImpl implements WalletDetailService {
  private final WalletDetailRepository walletDetailRepository;

  @Override
  public Mono<WalletDetail> save(WalletDetail walletDetail) {
    return this.walletDetailRepository.save(walletDetail);
  }

  @Override
  public Mono<WalletDetail> findByWalletId(Long walletId) {
    return this.walletDetailRepository.findByWalletId(walletId);
  }

  @Override
  public Mono<WalletDetail> findById(Long id) {
    return this.walletDetailRepository.findById(id);
  }
}
