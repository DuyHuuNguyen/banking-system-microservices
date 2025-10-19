package com.banking_app.wallet_service.infrastructure.service;

import com.banking_app.wallet_service.application.service.WalletService;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.server.grpc.wallet.InternalWalletRequest;
import com.example.server.grpc.wallet.InternalWalletResponse;
import com.example.server.grpc.wallet.WalletServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

@Log4j2
@GrpcService
@RequiredArgsConstructor
public class WalletServiceGrpcImpl extends WalletServiceGrpc.WalletServiceImplBase {
  private final WalletService walletService;

  @Override
  public void findWalletById(
      InternalWalletRequest request, StreamObserver<InternalWalletResponse> responseObserver) {
    this.walletService
        .findById(request.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
        .map(
            wallet ->
                InternalWalletResponse.newBuilder()
                    .setId(wallet.getId())
                    .setUserId(wallet.getUserId())
                    .setBalance(wallet.getBalance())
                    .setCurrency(wallet.getCurrency().toString())
                    .build())
        .subscribe(
            response -> {
              log.info("{}", response.toString());
              responseObserver.onNext(response);
              responseObserver.onCompleted();
            },
            error -> {
              responseObserver.onError(error);
            });
  }
}
