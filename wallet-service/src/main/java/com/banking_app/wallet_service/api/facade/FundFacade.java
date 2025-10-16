package com.banking_app.wallet_service.api.facade;

import com.banking_app.wallet_service.api.request.UpsertFundRequest;
import com.banking_app.wallet_service.api.response.FundResponse;
import com.example.base.BaseResponse;
import java.util.List;
import reactor.core.publisher.Mono;

public interface FundFacade {
  Mono<BaseResponse<Void>> createFund(UpsertFundRequest upsertFundRequest);

  Mono<BaseResponse<Void>> updateFundById(UpsertFundRequest upsertFundRequest);

  Mono<BaseResponse<Void>> deleteFundById(Long id);

  Mono<BaseResponse<List<FundResponse>>> findAllFund(Long walletId);
}
