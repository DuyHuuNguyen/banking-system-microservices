package com.banking_app.wallet_service.infrastructure.service;

import com.banking_app.wallet_service.application.service.FundService;
import com.banking_app.wallet_service.domain.entity.fund.Fund;
import com.banking_app.wallet_service.domain.repository.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FundServiceImpl implements FundService {
  private final FundRepository fundRepository;

  @Override
  public Flux<Fund> findByWalletId(Long walletId) {
    return this.fundRepository.findByWalletId(walletId);
  }
}
