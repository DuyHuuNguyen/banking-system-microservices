package com.banking_app.user_service.infrastructure.facade;

import com.banking_app.user_service.api.facade.IdentifyDocumentInformationFacade;
import com.banking_app.user_service.api.request.UpsertIdentificationDocumentInformationRequest;
import com.banking_app.user_service.api.response.IdentificationDocumentDetailResponse;
import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.service.DocumentLocationDetailService;
import com.banking_app.user_service.application.service.IdentifyDocumentInformationService;
import com.banking_app.user_service.application.service.ProducerMessageUpdateAccountService;
import com.banking_app.user_service.application.service.UserService;
import com.banking_app.user_service.domain.entity.document_location_detail.DocumentLocationDetail;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import com.example.base.BaseResponse;
import com.example.dto.UpdatingAccountMessage;
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
public class IdentifyDocumentInformationFacadeImpl implements IdentifyDocumentInformationFacade {

  private final IdentifyDocumentInformationService identifyDocumentInformationService;
  private final DocumentLocationDetailService documentLocationDetailService;
  private final UserService userService;
  private final ProducerMessageUpdateAccountService producerMessageUpdateAccountService;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> updateIdentifyDocument(
      UpsertIdentificationDocumentInformationRequest
          upsertIdentificationDocumentInformationRequest) {
    var updatingAccountMessage =
        UpdatingAccountMessage.builder()
            .personalId(upsertIdentificationDocumentInformationRequest.getPersonalId())
            .email("")
            .userId(-1L)
            .build();
    return this.identifyDocumentInformationService
        .findById(upsertIdentificationDocumentInformationRequest.getId())
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.IDENTITY_DOCUMENT_NOT_FOUND)))
        .map(
            identityDocumentInformation ->
                this.updateInfoIdentityDocumentInformation(
                    identityDocumentInformation,
                    upsertIdentificationDocumentInformationRequest
                        .getIdentityDocumentInformationDTO()))
        .flatMap(
            identityDocumentInformationUpdated ->
                this.documentLocationDetailService
                    .findById(identityDocumentInformationUpdated.getLocationIssuePlaceId())
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.LOCATION_NOT_FOUND)))
                    .map(
                        documentLocationDetail ->
                            this.updateInfoDocumentLocationDetail(
                                documentLocationDetail,
                                upsertIdentificationDocumentInformationRequest
                                    .getLocationOfIdentifyDocumentInformationDTO()))
                    .flatMap(
                        documentLocationDetailUpdated ->
                            Mono.when(
                                    this.identifyDocumentInformationService.save(
                                        identityDocumentInformationUpdated),
                                    this.documentLocationDetailService.save(
                                        documentLocationDetailUpdated))
                                .doOnError(
                                    throwable -> {
                                      log.error("Update Document is cancel");
                                      throw new PermissionDeniedException(
                                          ErrorCode.INFO_IDENTIFICATION_DOCUMENT_INVALID);
                                    })
                                .then(
                                    this.sendMessageUpdateAccountService(
                                        upsertIdentificationDocumentInformationRequest.getId(),
                                        updatingAccountMessage))
                                .thenReturn(BaseResponse.ok())));
  }

  @Override
  public Mono<BaseResponse<IdentificationDocumentDetailResponse>> findDetailById(Long id) {
    return this.identifyDocumentInformationService
        .findById(id)
        .switchIfEmpty(
            Mono.error(new EntityNotFoundException(ErrorCode.IDENTITY_DOCUMENT_NOT_FOUND)))
        .flatMap(
            identityDocumentInformation ->
                this.documentLocationDetailService
                    .findById(identityDocumentInformation.getLocationIssuePlaceId())
                    .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(ErrorCode.LOCATION_NOT_FOUND)))
                    .map(
                        documentLocationDetail ->
                            IdentificationDocumentDetailResponse.builder()
                                .id(identityDocumentInformation.getId())
                                .personalId(
                                    identityDocumentInformation.getPersonalIdentificationNumber())
                                .issuedAt(identityDocumentInformation.getIssuedAt())
                                .citizenIdFront(identityDocumentInformation.getCitizenIdFront())
                                .citizenIdBack(identityDocumentInformation.getCitizenIdBack())
                                .locationIssuePlaceId(
                                    identityDocumentInformation.getLocationIssuePlaceId())
                                .country(documentLocationDetail.getCountry())
                                .province(documentLocationDetail.getProvince())
                                .district(documentLocationDetail.getDistrict())
                                .ward(documentLocationDetail.getWard())
                                .street(documentLocationDetail.getStreet())
                                .homesNumber(documentLocationDetail.getHomesNumber())
                                .build())
                    .map(
                        identityDocumentInformationDetailResponse ->
                            BaseResponse.build(identityDocumentInformationDetailResponse, true)));
  }

  private Mono<Object> sendMessageUpdateAccountService(
      Long identificationDocumentInformationId, UpdatingAccountMessage updatingAccountMessage) {
    return this.userService
        .findByIdentificationDocumentInformationId(identificationDocumentInformationId)
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.USER_NOT_FOUND)))
        .flatMap(
            user -> {
              updatingAccountMessage.addEmail(user.getEmail());
              updatingAccountMessage.addUserId(user.getId());
              return Mono.fromRunnable(
                  () ->
                      this.producerMessageUpdateAccountService.updateInfoAccount(
                          updatingAccountMessage));
            });
  }

  private IdentityDocumentInformation updateInfoIdentityDocumentInformation(
      IdentityDocumentInformation identityDocumentInformation,
      IdentityDocumentInformationDTO identityDocumentInformationDTO) {
    identityDocumentInformation.updateInfo(identityDocumentInformationDTO);
    return identityDocumentInformation;
  }

  private DocumentLocationDetail updateInfoDocumentLocationDetail(
      DocumentLocationDetail documentLocationDetail, LocationDTO documentLocationDetailDTO) {
    documentLocationDetail.updateInfo(documentLocationDetailDTO);
    return documentLocationDetail;
  }
}
