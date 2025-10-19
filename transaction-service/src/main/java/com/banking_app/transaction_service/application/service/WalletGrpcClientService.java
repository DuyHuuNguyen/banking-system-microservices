package com.banking_app.transaction_service.application.service;

import com.example.server.grpc.wallet.InternalWalletResponse;
import reactor.core.publisher.Mono;

public interface WalletGrpcClientService {
  Mono<InternalWalletResponse> findWalletById(Long id);
}
