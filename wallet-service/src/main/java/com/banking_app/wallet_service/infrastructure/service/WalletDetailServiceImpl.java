package com.banking_app.wallet_service.infrastructure.service;

import com.banking_app.wallet_service.application.service.WalletDetailService;
import com.banking_app.wallet_service.domain.repository.WalletDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletDetailServiceImpl implements WalletDetailService {
  private final WalletDetailRepository walletDetailRepository;
}
