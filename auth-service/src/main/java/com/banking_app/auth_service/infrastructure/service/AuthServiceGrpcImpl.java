package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.AccountRoleService;
import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.JwtService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.account.Account;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.server.grpc.AccessTokenRequest;
import com.example.server.grpc.AuthResponse;
import com.example.server.grpc.AuthTokenServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AuthServiceGrpcImpl extends AuthTokenServiceGrpc.AuthTokenServiceImplBase {
  private final JwtService jwtService;
  private final AccountService accountService;
  private final AccountRoleService accountRoleService;
  private final RoleService roleService;

  //    private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Override
  public void parseToken(
      AccessTokenRequest request, StreamObserver<AuthResponse> responseObserver) {
    String accessToken = request.getAccessToken();
    var isValidateToken = this.jwtService.validateToken(accessToken);
    if (!isValidateToken)
      responseObserver.onError(new JwtException(ErrorCode.JWT_INVALID.getMessage()));

    String personalIdentifyInformation =
        this.jwtService.getPersonalIdentificationNumberFromJwtToken(accessToken);
    log.info("personalIdentifyInformation : {}", personalIdentifyInformation);
    accountService
        .findByPersonalIdentificationNumber(personalIdentifyInformation)
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
        .flatMap(this::buildAuthResponse)
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

  private Mono<AuthResponse> buildAuthResponse(Account account) {
    return this.roleService
        .findRolesByAccountId(account.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
        .map(role -> role.getRoleName().getContent())
        .collectList()
        .flatMap(
            roles ->
                Mono.just(
                    AuthResponse.newBuilder()
                        .setAccountId(account.getId())
                        .setUserId(account.getUserId())
                        .setEmail(account.getEmail())
                        .setPhone(account.getPhone())
                        .setOtp(account.getOtp())
                        .setPersonalId(account.getPersonalIdentificationNumber())
                        .setIsActive(account.isActive())
                        .setIsEnabled(true)
                        .addAllRoles(roles)
                        .build()));
  }
}
