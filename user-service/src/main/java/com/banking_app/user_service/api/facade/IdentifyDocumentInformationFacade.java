package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.IdentificationDocumentInfoCriteria;
import com.banking_app.user_service.api.request.UpsertIdentificationDocumentInformationRequest;
import com.banking_app.user_service.api.response.IdentifyDocumentDetailResponse;
import com.banking_app.user_service.api.response.IdentityDocumentInformationResponse;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import reactor.core.publisher.Mono;

public interface IdentifyDocumentInformationFacade {
  Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertIdentificationDocumentInformationRequest
          upsertIdentificationDocumentInformationRequest);

  Mono<BaseResponse<IdentifyDocumentDetailResponse>> findDetailById(Long id);

  Mono<BaseResponse<PaginationResponse<IdentityDocumentInformationResponse>>> findByFilter(
      IdentificationDocumentInfoCriteria identificationDocumentInfoCriteria);

  Mono<BaseResponse<IdentityDocumentInformationResponse>> findIdentifyDocumentInformationProfile();
}
