package com.banking_app.user_service.infrastructure.facade;

import com.banking_app.user_service.api.facade.UserFacade;
import com.banking_app.user_service.api.request.CreateUserRequest;
import com.banking_app.user_service.api.request.UpdateUserRequest;
import com.banking_app.user_service.api.request.UserCriteria;
import com.banking_app.user_service.api.response.ProfileResponse;
import com.banking_app.user_service.api.response.UserDetailResponse;
import com.banking_app.user_service.application.dto.IdentifyDocumentInformationWithLocationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationWithLocationDTO;
import com.banking_app.user_service.application.dto.UserDTO;
import com.banking_app.user_service.application.mapper.DocumentLocationDetailMapper;
import com.banking_app.user_service.application.mapper.IdentifyDocumentInformationMapper;
import com.banking_app.user_service.application.mapper.PersonalInformationMapper;
import com.banking_app.user_service.application.mapper.UserLocationDetailMapper;
import com.banking_app.user_service.application.service.*;
import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import com.banking_app.user_service.domain.entity.user.User;
import com.banking_app.user_service.domain.entity.user_location_detail.UserLocationDetail;
import com.banking_app.user_service.infrastructure.security.SecurityUserDetails;
import com.banking_app.user_service.infrastructure.util.UserSpecification;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import com.example.dto.UpdatingAccountMessage;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final IdentifyDocumentInformationService identifyDocumentInformationService;
  private final DocumentLocationDetailService documentLocationDetailService;
  private final PersonalInformationService personalInformationService;
  private final UserLocationDetailService userLocationDetailService;
  private final ProducerMessageUpdateAccountService producerMessageUpdateAccountService;

  private final IdentifyDocumentInformationMapper identifyDocumentInformationMapper;
  private final PersonalInformationMapper personalInformationMapper;
  private final UserLocationDetailMapper userLocationDetailMapper;
  private final DocumentLocationDetailMapper documentLocationDetailMapper;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> signUp(CreateUserRequest upsertUserRequest) {
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

    var personalInformation =
        this.personalInformationMapper.toPersonalInformation(
            upsertUserRequest.getPersonalInformationDTO());

    Mono<IdentityDocumentInformation> identityDocumentInformationMono =
        this.identifyDocumentInformationService.save(identifyDocumentInformation);

    Mono<PersonalInformation> personalInformationMono =
        this.personalInformationService.save(personalInformation);

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

  public Mono<BaseResponse<UserDetailResponse>> findDetailById(Long id) {
    return this.userService
        .findById(id)
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)))
        .flatMap(
            user -> {
              var identityDocumentInformationCompletableFuture =
                  this.findIdentityDocumentInfoWithLocation(
                      user.getIdentifyDocumentInformationId());

              var personalInformationCompletableFuture =
                  this.findPersonalInformationWithLocation(user.getPersonalInformationId());

              CompletableFuture<UserDetailResponse> combined =
                  CompletableFuture.allOf(
                          identityDocumentInformationCompletableFuture,
                          personalInformationCompletableFuture)
                      .thenApply(
                          v -> {
                            var identifyDocumentInformationWithLocationDTO =
                                identityDocumentInformationCompletableFuture.join();
                            var personalInformationWithLocationDTO =
                                personalInformationCompletableFuture.join();
                            return UserDetailResponse.builder()
                                .id(user.getId())
                                .userDTO(
                                    UserDTO.builder()
                                        .email(user.getEmail())
                                        .phone(user.getPhone())
                                        .build())
                                .personalInformationDTO(
                                    personalInformationWithLocationDTO.getPersonalInformationDTO())
                                .locationOfPersonalInformationDTO(
                                    personalInformationWithLocationDTO.getLocationDTO())
                                .identityDocumentInformationDTO(
                                    identifyDocumentInformationWithLocationDTO
                                        .getIdentityDocumentInformationDTO())
                                .locationOfIdentityDocumentInformationDTO(
                                    identifyDocumentInformationWithLocationDTO.getLocationDTO())
                                .build();
                          });
              return Mono.fromFuture(combined)
                  .map(userDetailResponse -> BaseResponse.build(userDetailResponse, true));
            });
  }

  public CompletableFuture<PersonalInformationWithLocationDTO> findPersonalInformationWithLocation(
      Long personalInformationId) {
    return personalInformationService
        .findById(personalInformationId)
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
        .map(personalInformationMapper::toPersonalInformationDTO)
        .flatMap(
            personalInformationDTO ->
                userLocationDetailService
                    .findById(personalInformationDTO.getLocationUserDetailId())
                    .switchIfEmpty(
                        Mono.error(
                            new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
                    .map(userLocationDetailMapper::toUserLocationDetailDTO)
                    .map(
                        userLocationDetailDTO ->
                            PersonalInformationWithLocationDTO.builder()
                                .personalInformationDTO(personalInformationDTO)
                                .locationDTO(userLocationDetailDTO)
                                .build()))
        .toFuture();
  }

  public CompletableFuture<IdentifyDocumentInformationWithLocationDTO>
      findIdentityDocumentInfoWithLocation(Long identifyDocumentInformationId) {
    return identifyDocumentInformationService
        .findById(identifyDocumentInformationId)
        .map(identifyDocumentInformationMapper::toIdentityDocumentInformationDTO)
        .flatMap(
            identityDocumentInformationDTO ->
                documentLocationDetailService
                    .findById(identityDocumentInformationDTO.getLocationIssuePlaceId())
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
                    .map(documentLocationDetailMapper::toDocumentLocationDetailDTO)
                    .map(
                        locationIssuePlaceDTO ->
                            IdentifyDocumentInformationWithLocationDTO.builder()
                                .identityDocumentInformationDTO(identityDocumentInformationDTO)
                                .locationDTO(locationIssuePlaceDTO)
                                .build()))
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.IDENTITY_DOCUMENT_NOT_FOUND)))
        .toFuture();
  }

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> updateUser(UpdateUserRequest updateUserRequest) {
    return this.userService
        .findById(updateUserRequest.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)))
        .flatMap(
            user -> {
              var updateAccountMessage =
                  UpdatingAccountMessage.builder()
                      .userId(user.getId())
                      .personalId(updateUserRequest.getPersonalId())
                      .email(user.getEmail())
                      .build();
              return Mono.when(
                      this.updatePersonalInformationAndLocationById(
                          user.getPersonalInformationId(),
                          updateUserRequest.getPersonalInformationWithLocationDTO()),
                      this.updateIdentifyDocumentInformationAndLocationById(
                          user.getIdentifyDocumentInformationId(),
                          updateUserRequest.getIdentityDocumentInformationDTO()))
                  .doOnError(
                      throwable -> {
                        throw new PermissionDeniedException(ErrorCode.INFO_USER_INVALID);
                      })
                  .then(
                      Mono.fromRunnable(
                          () ->
                              this.producerMessageUpdateAccountService.updateInfoAccount(
                                  updateAccountMessage)))
                  .then(Mono.fromCallable(BaseResponse::ok));
            });
  }

  private Mono<UserLocationDetail> updatePersonalInformationAndLocationById(
      Long id, PersonalInformationWithLocationDTO personalInformationWithLocationDTO) {
    return this.personalInformationService
        .findById(id)
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.PERSONAL_INFORMATION_NOT_FOUND)))
        .flatMap(
            personalInformation -> {
              personalInformation.updateInfo(
                  personalInformationWithLocationDTO.getPersonalInformationDTO());
              return this.personalInformationService
                  .save(personalInformation)
                  .flatMap(
                      personalInformationResponse ->
                          this.userLocationDetailService
                              .findById(personalInformation.getLocationUserDetailId())
                              .switchIfEmpty(
                                  Mono.error(
                                      new EntityNotFoundException(ErrorCode.LOCATION_NOT_FOUND)))
                              .flatMap(
                                  userLocationDetail -> {
                                    userLocationDetail.updateInfo(
                                        personalInformationWithLocationDTO.getLocationDTO());
                                    return this.userLocationDetailService.save(userLocationDetail);
                                  }));
            });
  }

  private Mono<DocumentLocationDetail> updateIdentifyDocumentInformationAndLocationById(
      Long id,
      IdentifyDocumentInformationWithLocationDTO identifyDocumentInformationWithLocationDTO) {
    return this.identifyDocumentInformationService
        .findById(id)
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.IDENTITY_DOCUMENT_NOT_FOUND)))
        .flatMap(
            identityDocumentInformation -> {
              identityDocumentInformation.updateInfo(
                  identifyDocumentInformationWithLocationDTO.getIdentityDocumentInformationDTO());
              return this.identifyDocumentInformationService
                  .save(identityDocumentInformation)
                  .flatMap(
                      identityDocumentInformationResponse ->
                          this.documentLocationDetailService
                              .findById(
                                  identityDocumentInformationResponse.getLocationIssuePlaceId())
                              .switchIfEmpty(
                                  Mono.error(
                                      new EntityNotFoundException(ErrorCode.LOCATION_NOT_FOUND)))
                              .flatMap(
                                  documentLocationDetail -> {
                                    documentLocationDetail.updateInfo(
                                        identifyDocumentInformationWithLocationDTO
                                            .getLocationDTO());
                                    return this.documentLocationDetailService.save(
                                        documentLocationDetail);
                                  }));
            });
  }

  @Override
  public Mono<BaseResponse<PaginationResponse<ProfileResponse>>> findByFilter(
      UserCriteria userCriteria) {
    UserSpecification userSpecification =
        UserSpecification.builder()
            .pageNumber(userCriteria.getCurrentPage())
            .pageSize(userCriteria.getPageSize())
            .createdAtInMonth(userCriteria.getMonthCreatedAt())
            .createdAt(userCriteria.getCreatedAt())
            .build();
    return this.userService
        .findAll(userSpecification)
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .flatMap(
            user ->
                Mono.zip(
                    Mono.just(user),
                    this.personalInformationService.findById(user.getPersonalInformationId())))
        .map(
            tuple -> {
              var user = tuple.getT1();
              var personalInformation = tuple.getT2();
              log.info("thread after zip !");
              return ProfileResponse.builder()
                  .id(user.getId())
                  .sex(personalInformation.getSex())
                  .personalPhoto(personalInformation.getPersonalPhoto())
                  .fullName(personalInformation.getFullName())
                  .email(user.getEmail())
                  .phone(user.getPhone())
                  .build();
            })
        .sequential()
        .collectList()
        .flatMap(
            profiles -> {
              return Mono.just(
                  BaseResponse.<PaginationResponse<ProfileResponse>>build(
                      PaginationResponse.<ProfileResponse>builder()
                          .data(profiles)
                          .pageSize(userCriteria.getPageSize())
                          .currentPage(userCriteria.getCurrentPage())
                          .build(),
                      true));
            });
  }
}
