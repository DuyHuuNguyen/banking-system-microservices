package com.banking_app.wallet_service.api.facade;

import com.banking_app.wallet_service.api.request.UpsertFundRequest;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface FundFacade {
  Mono<BaseResponse<Void>> createFund(UpsertFundRequest upsertFundRequest);

  Mono<BaseResponse<Void>> updateFundById(UpsertFundRequest upsertFundRequest);

  Mono<BaseResponse<Void>> deleteFundById(Long id);
}
