package com.banking_app.wallet_service.infrastructure.service;

import com.banking_app.wallet_service.application.service.FundService;
import com.banking_app.wallet_service.domain.repository.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundServiceImpl implements FundService {
  private final FundRepository fundRepository;
}
