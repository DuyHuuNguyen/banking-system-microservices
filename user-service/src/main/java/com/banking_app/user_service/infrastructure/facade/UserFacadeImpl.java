package com.banking_app.user_service.infrastructure.facade;

import com.banking_app.user_service.api.facade.UserFacade;
import com.banking_app.user_service.api.request.UpsertUserRequest;
import com.banking_app.user_service.api.response.ProfileResponse;
import com.banking_app.user_service.application.mapper.DocumentLocationDetailMapper;
import com.banking_app.user_service.application.mapper.IdentifyDocumentInformationMapper;
import com.banking_app.user_service.application.mapper.PersonalInformationMapper;
import com.banking_app.user_service.application.mapper.UserLocationDetailMapper;
import com.banking_app.user_service.application.service.*;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import com.banking_app.user_service.domain.entity.user.User;
import com.banking_app.user_service.infrastructure.security.SecurityUserDetails;
import com.example.base.BaseResponse;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final IdentifyDocumentInformationService identifyDocumentInformationService;
  private final DocumentLocationDetailService documentLocationDetailService;
  private final PersonalInformationService personalInformationService;
  private final UserLocationDetailService userLocationDetailService;

  private final IdentifyDocumentInformationMapper identifyDocumentInformationMapper;
  private final PersonalInformationMapper personalInformationMapper;
  private final UserLocationDetailMapper userLocationDetailMapper;
  private final DocumentLocationDetailMapper documentLocationDetailMapper;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> signUp(UpsertUserRequest upsertUserRequest) {
    var user =
        User.builder()
            .email(upsertUserRequest.getUserDTO().getEmail())
            .phone(upsertUserRequest.getUserDTO().getPhone())
            .identifyDocumentInformationId(-1L)
            .personalInformationId(-1L)
            .build();

    var identifyDocumentInformation =
        this.identifyDocumentInformationMapper.toIdentityDocumentInformation(
            upsertUserRequest.getIdentityDocumentInformationDTO());

    var documentLocationDetail =
        this.documentLocationDetailMapper.toDocumentLocationDetail(
            upsertUserRequest.getLocationOfPersonalInformationDTO());

    var personalInformation =
        this.personalInformationMapper.toPersonalInformation(
            upsertUserRequest.getPersonalInformationDTO());

    var userLocationDetail =
        this.userLocationDetailMapper.toUserLocationDetail(
            upsertUserRequest.getLocationOfPersonalInformationDTO());

    Mono<IdentityDocumentInformation> identityDocumentInformationMono =
        this.documentLocationDetailService
            .save(documentLocationDetail)
            .flatMap(
                createdDocumentLocationDetail -> {
                  identifyDocumentInformation.addLocationIssuePlaceId(
                      createdDocumentLocationDetail.getId());
                  return this.identifyDocumentInformationService.save(identifyDocumentInformation);
                });

    Mono<PersonalInformation> personalInformationMono =
        this.userLocationDetailService
            .save(userLocationDetail)
            .flatMap(
                createdUserLocationDetail -> {
                  personalInformation.addLocationUserDetailId(createdUserLocationDetail.getId());
                  return this.personalInformationService.save(personalInformation);
                });

    return Mono.zip(identityDocumentInformationMono, personalInformationMono)
        .onErrorMap(exception -> new PermissionDeniedException(ErrorCode.JWT_INVALID))
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.JWT_INVALID)))
        .flatMap(
            tuple -> {
              IdentityDocumentInformation createdIdentityDocumentInformation = tuple.getT1();
              PersonalInformation createdPersonalInformation = tuple.getT2();

              log.info("save identity document {}", createdIdentityDocumentInformation);
              log.info("save personal information {}", createdPersonalInformation);

              user.addPersonalInformationId(createdPersonalInformation.getId());
              user.addIdentifyDocumentInformationId(createdIdentityDocumentInformation.getId());

              return userService.save(user);
            })
        .then(Mono.just(BaseResponse.ok()));
  }

  @Override
  public Mono<BaseResponse<ProfileResponse>> getProfile() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(authentication -> (SecurityUserDetails) authentication.getPrincipal())
        .flatMap(
            principal -> {
              return this.userService
                  .findById(principal.getUserId())
                  .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)))
                  .flatMap(
                      user -> {
                        return this.personalInformationService
                            .findById(user.getPersonalInformationId())
                            .flatMap(
                                personalInformation -> {
                                  var profileResponse =
                                      ProfileResponse.builder()
                                          .id(user.getId())
                                          .sex(personalInformation.getSex())
                                          .personalPhoto(personalInformation.getPersonalPhoto())
                                          .fullName(personalInformation.getFullName())
                                          .email(user.getEmail())
                                          .phone(user.getPhone())
                                          .build();

                                  return Mono.just(BaseResponse.build(profileResponse, true));
                                });
                      });
            });
  }
}
