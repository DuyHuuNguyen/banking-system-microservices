package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.UpsertIdentificationDocumentInformationRequest;
import com.banking_app.user_service.api.response.IdentificationDocumentDetailResponse;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface IdentifyDocumentInformationFacade {
  Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertIdentificationDocumentInformationRequest
          upsertIdentificationDocumentInformationRequest);

  Mono<BaseResponse<IdentificationDocumentDetailResponse>> findDetailById(Long id);
}
