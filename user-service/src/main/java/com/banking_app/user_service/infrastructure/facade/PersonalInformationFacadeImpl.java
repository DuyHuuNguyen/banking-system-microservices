package com.banking_app.user_service.infrastructure.facade;

import com.banking_app.user_service.api.facade.PersonalInformationFacade;
import com.banking_app.user_service.api.request.ChangePersonalPhotoRequest;
import com.banking_app.user_service.api.request.PersonalInformationCriteria;
import com.banking_app.user_service.api.request.UpsertPersonalInformationRequest;
import com.banking_app.user_service.api.response.PersonalInformationDetailResponse;
import com.banking_app.user_service.api.response.PersonalInformationResponse;
import com.banking_app.user_service.application.service.PersonalInformationService;
import com.banking_app.user_service.application.service.UserLocationDetailService;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import com.banking_app.user_service.infrastructure.security.SecurityUserDetails;
import com.banking_app.user_service.infrastructure.util.PersonalInformationSpecification;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
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
                      Mono.error(new EntityNotFoundException(ErrorCode.LOCATION_NOT_FOUND)))
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

  @Override
  public Mono<BaseResponse<PersonalInformationDetailResponse>> findPersonalInformationProfile() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails -> {
              return this.personalInformationService
                  .findByUserId(securityUserDetails.getUserId())
                  .switchIfEmpty(
                      Mono.error(
                          new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
                  .flatMap(this::buildPersonalInformationDetailResponse);
            });
  }

  @Override
  public Mono<BaseResponse<Void>> changePersonalPhoto(
      ChangePersonalPhotoRequest changePersonalPhotoRequest) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(SecurityUserDetails.class)
        .flatMap(
            securityUserDetails ->
                this.personalInformationService
                    .findByUserId(securityUserDetails.getUserId())
                    .switchIfEmpty(
                        Mono.error(
                            new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
                    .flatMap(
                        personalInformation -> {
                          personalInformation.changePersonalPhoto(
                              changePersonalPhotoRequest.getPersonalPhoto());
                          return this.personalInformationService.save(personalInformation);
                        })
                    .doOnError(
                        throwable -> {
                          throw new PermissionDeniedException(ErrorCode.INFO_USER_INVALID);
                        })
                    .thenReturn(BaseResponse.ok()));
  }

  @Override
  public Mono<BaseResponse<PersonalInformationDetailResponse>> findDetailById(Long id) {
    return this.personalInformationService
        .findById(id)
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
        .flatMap(this::buildPersonalInformationDetailResponse);
  }

  @Override
  public Mono<BaseResponse<PaginationResponse<PersonalInformationResponse>>> findByFilter(
      PersonalInformationCriteria personalInformationCriteria) {
    var personalInformationSpecification =
        PersonalInformationSpecification.builder()
            .createdAt(personalInformationCriteria.getCreatedAt())
            .sex(personalInformationCriteria.getSex())
            .createdAtWithinRange(personalInformationCriteria.getWithinDateRange())
            .pageNumber(personalInformationCriteria.getCurrentPage())
            .pageSize(personalInformationCriteria.getPageSize())
            .build();
    return this.personalInformationService
        .findAll(personalInformationSpecification)
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
        .map(personalInformation -> PersonalInformationResponse.builder().build())
        .collectList()
        .map(
            personalInformationResponses ->
                PaginationResponse.<PersonalInformationResponse>builder()
                    .data(personalInformationResponses)
                    .pageSize(personalInformationCriteria.getPageSize())
                    .currentPage(personalInformationCriteria.getCurrentPage())
                    .build())
        .map(
            personalInformationResponsePaginationResponse ->
                BaseResponse.build(personalInformationResponsePaginationResponse, true));
  }

  private Mono<BaseResponse<PersonalInformationDetailResponse>>
      buildPersonalInformationDetailResponse(PersonalInformation personalInformation) {
    return this.userLocationDetailService
        .findById(personalInformation.getLocationUserDetailId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.LOCATION_NOT_FOUND)))
        .map(
            userLocationDetail ->
                BaseResponse.build(
                    PersonalInformationDetailResponse.builder()
                        .id(personalInformation.getId())
                        .fullName(personalInformation.getFullName())
                        .dateOfBirth(personalInformation.getDateOfBirth())
                        .sex(personalInformation.getSex())
                        .personalPhoto(personalInformation.getPersonalPhoto())
                        .locationUserDetailId(personalInformation.getLocationUserDetailId())
                        .country(userLocationDetail.getCountry())
                        .province(userLocationDetail.getProvince())
                        .district(userLocationDetail.getDistrict())
                        .ward(userLocationDetail.getWard())
                        .street(userLocationDetail.getStreet())
                        .homesNumber(userLocationDetail.getHomesNumber())
                        .build(),
                    true))
        .flatMap(Mono::just);
  }
}
