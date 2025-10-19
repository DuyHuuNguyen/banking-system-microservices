package com.banking_app.transaction_service.infrastructure.facade;

import com.banking_app.transaction_service.api.facde.TransactionFacade;
import com.banking_app.transaction_service.api.request.CreateTransactionRequest;
import com.banking_app.transaction_service.application.service.HandleTransactionProducerService;
import com.banking_app.transaction_service.application.service.WalletGrpcClientService;
import com.banking_app.transaction_service.infrastructure.security.SecurityUserDetails;
import com.example.base.BaseResponse;
import com.example.enums.ErrorCode;
import com.example.exception.CacheException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionFacadeImpl implements TransactionFacade {
  private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
  private final WalletGrpcClientService walletService;
  private final HandleTransactionProducerService handleTransactionProducerService;

  @Override
  public Mono<BaseResponse<Void>> createPayment(CreateTransactionRequest createTransactionRequest) {
    return this.reactiveRedisTemplate
        .hasKey(createTransactionRequest.getIdempotencyKey())
        .flatMap(
            isHasIdempotencyKey ->
                isHasIdempotencyKey
                    ? this.storeIdempotencyKey(createTransactionRequest.getIdempotencyKey())
                    : this.pushTransaction(createTransactionRequest));
  }

  private Mono<BaseResponse<Void>> storeIdempotencyKey(final String idempotencyKey) {
    return this.reactiveRedisTemplate
        .opsForValue()
        .set(idempotencyKey, UUID.randomUUID().toString())
        .doOnError(
            haha -> {
              throw new CacheException(ErrorCode.CAN_NOT_CACHE);
            })
        .then(Mono.just(BaseResponse.ok()));
  }

  private Mono<BaseResponse<Void>> pushTransaction(
      CreateTransactionRequest createTransactionRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails -> {
              return Mono.just(BaseResponse.ok());
            });
  }
}
