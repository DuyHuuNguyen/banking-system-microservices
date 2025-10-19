package com.banking_app.transaction_service.api.facde;

import com.banking_app.transaction_service.api.request.CreateTransactionRequest;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface TransactionFacade {
  Mono<BaseResponse<Void>> createPayment(CreateTransactionRequest createPaymentRequest);
}
