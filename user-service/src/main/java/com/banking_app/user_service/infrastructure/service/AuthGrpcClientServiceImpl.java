package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.AuthGrpcClientService;
import com.example.server.grpc.AccessTokenRequest;
import com.example.server.grpc.AuthResponse;
import com.example.server.grpc.AuthTokenServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class AuthGrpcClientServiceImpl implements AuthGrpcClientService {

    @GrpcClient("hello")
    private AuthTokenServiceGrpc.AuthTokenServiceBlockingStub authTokenServiceBlockingStub;

    @Override
    public Mono<AuthResponse> parseToken(String token) {
        AccessTokenRequest request = AccessTokenRequest.newBuilder()
                .setAccessToken(token)
                .build();
        return Mono.just(authTokenServiceBlockingStub.parseToken(request));
    }

    @PostConstruct
    public void run(){
        AccessTokenRequest request = AccessTokenRequest.newBuilder()
                .setAccessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0IiwiaWF0IjoxNzU4MzQ5NzE0LCJleHAiOjE3NTg2MDg5MTR9.pGqXPZdF-5EfUCN9ZxvQzi3PZb10syvExqWUhHl5KOH44pbMTXoZFHSmntIE8Z_zOAIPTrbpxeEECqs2AxKFqQ")
                .build();
      var i =  authTokenServiceBlockingStub.parseToken(request);
      log.info(i.getEmail()+" heehhe");
    }

}

@Configuration
class GrpcClientConfig {
//
//    @Bean
//    public ManagedChannel authChannel() {
//        return ManagedChannelBuilder.forAddress("localhost", 9090)
//                .usePlaintext() // tắt TLS, dev mode
//                .build();
//    }
//
//    @Bean
//    public AuthTokenServiceGrpc.AuthTokenServiceBlockingStub authBlockingStub(ManagedChannel channel) {
//        return AuthTokenServiceGrpc.newBlockingStub(channel);
//    }
}
