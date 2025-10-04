package com.banking_app.user_service.api.facade;

import com.banking_app.user_service.api.request.ChangePersonalPhotoRequest;
import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
import com.banking_app.user_service.api.response.PersonalInformationDetailResponse;
import com.example.base.BaseResponse;
import reactor.core.publisher.Mono;

public interface PersonalInformationFacade {
  Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertPersonalInformationRequest upsertPersonalInformationRequest);

  Mono<BaseResponse<PersonalInformationDetailResponse>> findPersonalInformationProfile();

  Mono<BaseResponse<Void>> changePersonalPhoto(
      ChangePersonalPhotoRequest changePersonalPhotoRequest);
}
