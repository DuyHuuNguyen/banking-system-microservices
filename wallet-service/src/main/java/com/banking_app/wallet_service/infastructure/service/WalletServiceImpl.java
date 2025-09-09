package com.banking_app.wallet_service.infastructure.service;

import com.banking_app.wallet_service.application.service.WalletService;
import com.banking_app.wallet_service.domain.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
  private final WalletRepository walletRepository;
}
