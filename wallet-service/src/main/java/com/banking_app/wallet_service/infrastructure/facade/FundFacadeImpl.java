package com.banking_app.wallet_service.infrastructure.facade;

import com.banking_app.wallet_service.api.facade.FundFacade;
import com.banking_app.wallet_service.api.request.UpsertFundRequest;
import com.banking_app.wallet_service.application.service.FundService;
import com.banking_app.wallet_service.application.service.WalletService;
import com.banking_app.wallet_service.domain.entity.fund.Fund;
import com.banking_app.wallet_service.infrastructure.security.SecurityUserDetails;
import com.example.base.BaseResponse;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FundFacadeImpl implements FundFacade {
  private final FundService fundService;
  private final WalletService walletService;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> createFund(UpsertFundRequest upsertFundRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails ->
                this.walletService
                    .findById(upsertFundRequest.getWalletId())
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                    .filter(
                        wallet ->
                            wallet.getUserId().equals(securityUserDetails.getUserId())
                                && wallet.isActive())
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                    .flatMap(
                        wallet -> {
                          var fund =
                              Fund.builder()
                                  .fundName(upsertFundRequest.getFundName())
                                  .balance(0d)
                                  .walletId(wallet.getId())
                                  .description(upsertFundRequest.getDescription())
                                  .build();
                          return this.fundService
                              .save(fund)
                              .doOnError(
                                  error -> {
                                    throw new PermissionDeniedException(
                                        ErrorCode.UPDATE_ENTITY_ERROR);
                                  });
                        }))
        .thenReturn(BaseResponse.ok());
  }

  @Override
  public Mono<BaseResponse<Void>> updateFundById(UpsertFundRequest upsertFundRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails ->
                this.fundService
                    .findById(upsertFundRequest.getId())
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.FUND_NOT_FOUND)))
                    .flatMap(
                        fund -> {
                          fund.changeName(upsertFundRequest.getFundName());
                          fund.changeDescription(upsertFundRequest.getDescription());
                          return this.fundService
                              .save(fund)
                              .doOnError(
                                  error -> {
                                    throw new PermissionDeniedException(
                                        ErrorCode.UPDATE_ENTITY_ERROR);
                                  });
                        }))
        .thenReturn(BaseResponse.ok());
  }

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> deleteFundById(Long id) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails ->
                this.fundService
                    .findByUserIdAndFundId(securityUserDetails.getUserId(), id)
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.FUND_NOT_FOUND)))
                    .flatMap(
                        fund -> {
                          fund.disable();
                          return this.fundService
                              .save(fund)
                              .doOnError(
                                  error -> {
                                    throw new PermissionDeniedException(
                                        ErrorCode.DENY_SOFT_DELETE_ENTITY);
                                  });
                        }))
        .thenReturn(BaseResponse.ok());
  }
}
