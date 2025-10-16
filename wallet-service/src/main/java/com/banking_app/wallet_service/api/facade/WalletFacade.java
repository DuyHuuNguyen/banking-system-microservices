package com.banking_app.wallet_service.api.facade;

import com.banking_app.wallet_service.api.request.UpsertWalletRequest;
import com.banking_app.wallet_service.api.response.WalletDetailResponse;
import com.banking_app.wallet_service.api.response.WalletResponse;
import com.example.base.BaseResponse;
import java.util.List;
import reactor.core.publisher.Mono;

public interface WalletFacade {
  Mono<BaseResponse<Void>> createWallet(UpsertWalletRequest upsertWalletRequest);

  Mono<BaseResponse<List<WalletResponse>>> findAllPersonalWallets();

  Mono<BaseResponse<Void>> updatePersonalWallet(UpsertWalletRequest upsertWalletRequest);

  Mono<BaseResponse<WalletDetailResponse>> findWalletDetailById(Long id);
}
