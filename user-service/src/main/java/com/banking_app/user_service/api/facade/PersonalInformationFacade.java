package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface PersonalInformationFacade {
  Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertPersonalInformationRequest upsertPersonalInformationRequest);
}
