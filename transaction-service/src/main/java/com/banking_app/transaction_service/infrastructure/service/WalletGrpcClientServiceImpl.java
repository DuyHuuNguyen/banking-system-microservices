package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.WalletGrpcClientService;
import com.example.server.grpc.wallet.InternalWalletRequest;
import com.example.server.grpc.wallet.InternalWalletResponse;
import com.example.server.grpc.wallet.WalletServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WalletGrpcClientServiceImpl implements WalletGrpcClientService {

  @GrpcClient("wallet-service")
  private WalletServiceGrpc.WalletServiceBlockingStub walletServiceBlockingStub;

  @Override
  public Mono<InternalWalletResponse> findWalletById(Long id) {
    InternalWalletRequest internalWalletRequest =
        InternalWalletRequest.newBuilder().setId(id).build();
    return Mono.just(walletServiceBlockingStub.findWalletById(internalWalletRequest));
  }
}
