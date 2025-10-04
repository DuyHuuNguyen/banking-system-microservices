package com.banking_app.user_service.infrastructure.facade;

import com.banking_app.user_service.api.facade.PersonalInformationFacade;
import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
import com.banking_app.user_service.application.service.PersonalInformationService;
import com.banking_app.user_service.application.service.UserLocationDetailService;
import com.example.base.BaseResponse;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class PersonalInformationFacadeImpl implements PersonalInformationFacade {
  private final PersonalInformationService personalInformationService;
  private final UserLocationDetailService userLocationDetailService;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertPersonalInformationRequest upsertPersonalInformationRequest) {
    return this.personalInformationService
        .findById(upsertPersonalInformationRequest.getId())
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
        .flatMap(
            personalInformation -> {
              personalInformation.updateInfo(
                  upsertPersonalInformationRequest.getPersonalInformationDTO());
              return this.userLocationDetailService
                  .findById(personalInformation.getLocationUserDetailId())
                  .switchIfEmpty(
                      Mono.error(
                          new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
                  .flatMap(
                      userLocationDetail -> {
                        userLocationDetail.updateInfo(
                            upsertPersonalInformationRequest.getLocationDTO());
                        return Mono.when(
                                userLocationDetailService.save(userLocationDetail),
                                this.personalInformationService.save(personalInformation))
                            .doOnError(
                                throwable -> {
                                  throw new PermissionDeniedException(ErrorCode.INFO_USER_INVALID);
                                })
                            .thenReturn(BaseResponse.ok());
                      });
            });
  }
}
