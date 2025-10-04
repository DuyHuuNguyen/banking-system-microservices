package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.ChangePersonalPhotoRequest;
import com.banking_app.user_service.api.request.PersonalInformationCriteria;
import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
import com.banking_app.user_service.api.response.PersonalInformationDetailResponse;
import com.banking_app.user_service.api.response.PersonalInformationResponse;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import reactor.core.publisher.Mono;

public interface PersonalInformationFacade {
  Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertPersonalInformationRequest upsertPersonalInformationRequest);

  Mono<BaseResponse<PersonalInformationDetailResponse>> findPersonalInformationProfile();

  Mono<BaseResponse<Void>> changePersonalPhoto(
      ChangePersonalPhotoRequest changePersonalPhotoRequest);

  Mono<BaseResponse<PersonalInformationDetailResponse>> findDetailById(Long id);

  Mono<BaseResponse<PaginationResponse<PersonalInformationResponse>>> findByFilter(
      PersonalInformationCriteria personalInformationCriteria);
}
