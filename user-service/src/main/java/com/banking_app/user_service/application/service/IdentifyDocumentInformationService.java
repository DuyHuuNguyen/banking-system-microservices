package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import com.banking_app.user_service.infrastructure.util.IdentificationDocumentInformationSpecification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IdentifyDocumentInformationService {
  Mono<IdentityDocumentInformation> save(IdentityDocumentInformation identityDocumentInformation);

  Mono<IdentityDocumentInformation> findById(Long id);

  Flux<IdentityDocumentInformation> findAll(
      IdentificationDocumentInformationSpecification
          identificationDocumentInformationSpecification);

  Mono<IdentityDocumentInformation> findByUserId(Long userId);
}
