package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.AuthGrpcClientService;
import com.example.server.grpc.AccessTokenRequest;
import com.example.server.grpc.AuthResponse;
import com.example.server.grpc.AuthTokenServiceGrpc;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class AuthGrpcClientServiceImpl implements AuthGrpcClientService {

  @GrpcClient("hello")
  private AuthTokenServiceGrpc.AuthTokenServiceBlockingStub authTokenServiceBlockingStub;

  @Override
  public Mono<AuthResponse> parseToken(String token) {
    AccessTokenRequest request = AccessTokenRequest.newBuilder().setAccessToken(token).build();
    return Mono.fromCallable(() -> authTokenServiceBlockingStub.parseToken(request))
        .materialize()
        .flatMap(
            signal -> {
              if (signal.isOnError()) {
                // Trả về 1 event khi có lỗi
                return Mono.just(AuthResponse.newBuilder().setIsEnabled(false).build());
              }
              // Trả về data gốc
              return Mono.just(signal.get());
            });
  }
}
