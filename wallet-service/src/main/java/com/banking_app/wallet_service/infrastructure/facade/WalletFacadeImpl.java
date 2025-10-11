package com.banking_app.wallet_service.infrastructure.facade;

import com.banking_app.wallet_service.api.facade.WalletFacade;
import com.banking_app.wallet_service.api.request.UpsertWalletRequest;
import com.banking_app.wallet_service.api.response.WalletDetailResponse;
import com.banking_app.wallet_service.api.response.WalletResponse;
import com.banking_app.wallet_service.application.dto.FundDTO;
import com.banking_app.wallet_service.application.service.FundService;
import com.banking_app.wallet_service.application.service.WalletDetailService;
import com.banking_app.wallet_service.application.service.WalletService;
import com.banking_app.wallet_service.domain.entity.wallet.Wallet;
import com.banking_app.wallet_service.domain.entity.wallet_detail.WalletDetail;
import com.banking_app.wallet_service.infrastructure.security.SecurityUserDetails;
import com.example.base.BaseResponse;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WalletFacadeImpl implements WalletFacade {
  private final WalletService walletService;
  private final WalletDetailService walletDetailService;
  private final FundService fundService;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> createWallet(UpsertWalletRequest upsertWalletRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails -> {
              Wallet wallet =
                  Wallet.builder()
                      .currency(upsertWalletRequest.getCurrency())
                      .walletDetailId(-1L)
                      .userId(securityUserDetails.getUserId())
                      .balance(0D)
                      .build();
              WalletDetail walletDetail =
                  WalletDetail.builder()
                      .walletName(upsertWalletRequest.getWalletName())
                      .description(upsertWalletRequest.getDescription())
                      .build();
              return this.walletDetailService
                  .save(walletDetail)
                  .switchIfEmpty(
                      Mono.error(new EntityNotFoundException(ErrorCode.CREATE_FAIL_WALLET)))
                  .flatMap(
                      walletDetailSaved -> {
                        wallet.addWalletDetailId(walletDetailSaved.getId());
                        return this.walletService
                            .save(wallet)
                            .switchIfEmpty(
                                Mono.error(
                                    new EntityNotFoundException(ErrorCode.CREATE_FAIL_WALLET)))
                            .then(Mono.just(BaseResponse.ok()));
                      });
            });
  }

  @Override
  public Mono<BaseResponse<List<WalletResponse>>> findAllPersonalWallets() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails ->
                this.walletService
                    .findByUserId(securityUserDetails.getUserId())
                    .filter(Wallet::isActive)
                    .collectList()
                    .flatMap(
                        wallets -> {
                          List<CompletableFuture<WalletResponse>> futures =
                              wallets.stream().map(this::buildWalletResponse).toList();
                          var combinationFutures =
                              CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                                  .thenApply(
                                      v -> futures.stream().map(CompletableFuture::join).toList());
                          return Mono.fromFuture(combinationFutures)
                              .map(walletResponses -> BaseResponse.build(walletResponses, true));
                        }));
  }

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> updatePersonalWallet(UpsertWalletRequest upsertWalletRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails -> {
              return this.walletService
                  .findById(upsertWalletRequest.getId())
                  .switchIfEmpty(
                      Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                  .flatMap(
                      wallet -> {
                        return this.walletDetailService
                            .findById(wallet.getWalletDetailId())
                            .switchIfEmpty(
                                Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                            .flatMap(
                                walletDetail -> {
                                  walletDetail.rename(upsertWalletRequest.getWalletName());
                                  walletDetail.changeDescription(walletDetail.getDescription());
                                  return this.walletDetailService.save(walletDetail);
                                });
                      })
                  .thenReturn(BaseResponse.ok());
            });
  }

  @Override
  public Mono<BaseResponse<WalletDetailResponse>> findWalletDetailById(Long id) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails ->
                this.walletService
                    .findById(id)
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                    .filter(wallet -> wallet.getUserId().equals(securityUserDetails.getUserId()))
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                    .flatMap(
                        wallet ->
                            this.walletDetailService
                                .findById(wallet.getWalletDetailId())
                                .switchIfEmpty(
                                    Mono.error(
                                        new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                                .map(
                                    walletDetail ->
                                        WalletDetailResponse.builder()
                                            .id(wallet.getId())
                                            .walletName(walletDetail.getWalletName())
                                            .currency(wallet.getCurrency())
                                            .balance(wallet.getBalance())
                                            .description(walletDetail.getDescription())
                                            .userId(wallet.getUserId())
                                            .build()))
                    .flatMap(
                        walletDetailResponse ->
                            this.fundService
                                .findByWalletId(walletDetailResponse.getId())
                                .switchIfEmpty(
                                    Mono.error(
                                        new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
                                .map(
                                    fund ->
                                        FundDTO.builder()
                                            .fundName(fund.getFundName())
                                            .balance(fund.getBalance())
                                            .description(fund.getDescription())
                                            .id(fund.getId())
                                            .build())
                                .collectList()
                                .map(
                                    fundDTOS -> {
                                      walletDetailResponse.addFundDTOS(fundDTOS);
                                      return BaseResponse.build(walletDetailResponse, true);
                                    })));
  }

  private CompletableFuture<WalletResponse> buildWalletResponse(Wallet wallet) {
    return this.walletDetailService
        .findByWalletId(wallet.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.WALLET_NOT_FOUND)))
        .map(
            walletDetail ->
                WalletResponse.builder()
                    .id(wallet.getId())
                    .walletName(walletDetail.getWalletName())
                    .currency(wallet.getCurrency())
                    .balance(wallet.getBalance())
                    .description(walletDetail.getDescription())
                    .userId(wallet.getUserId())
                    .build())
        .toFuture();
  }
}
