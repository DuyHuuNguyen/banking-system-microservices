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
                    .filter(wallet -> wallet.getUserId().equals(securityUserDetails.getUserId()))
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
                          return this.fundService.save(fund);
                        }))
        .thenReturn(BaseResponse.ok());
  }
}
