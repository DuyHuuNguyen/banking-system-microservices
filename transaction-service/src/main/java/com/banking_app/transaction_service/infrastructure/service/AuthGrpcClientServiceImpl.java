package com.banking_app.transaction_service.infrastructure.service;

import com.banking_app.transaction_service.application.service.AuthGrpcClientService;
import com.example.server.grpc.AccessTokenRequest;
import com.example.server.grpc.AuthResponse;
import com.example.server.grpc.AuthTokenServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthGrpcClientServiceImpl implements AuthGrpcClientService {
  @GrpcClient("parse-token")
  private AuthTokenServiceGrpc.AuthTokenServiceBlockingStub authTokenServiceBlockingStub;

  @Override
  public Mono<AuthResponse> parseToken(String token) {
    AccessTokenRequest request = AccessTokenRequest.newBuilder().setAccessToken(token).build();
    return Mono.fromCallable(() -> authTokenServiceBlockingStub.parseToken(request))
        .materialize()
        .flatMap(
            signal -> {
              if (signal.isOnError()) {
                return Mono.just(AuthResponse.newBuilder().setIsEnabled(false).build());
              }
              return Mono.just(signal.get());
            });
  }
}
