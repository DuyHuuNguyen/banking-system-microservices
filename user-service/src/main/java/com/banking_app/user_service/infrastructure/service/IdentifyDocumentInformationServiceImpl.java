package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.IdentifyDocumentInformationService;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import com.banking_app.user_service.domain.repository.IdentifyDocumentInformationRepository;
import com.banking_app.user_service.infrastructure.util.IdentificationDocumentInformationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentifyDocumentInformationServiceImpl implements IdentifyDocumentInformationService {
  private final IdentifyDocumentInformationRepository identifyDocumentInformationRepository;
  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<IdentityDocumentInformation> save(
      IdentityDocumentInformation identityDocumentInformation) {
    return identifyDocumentInformationRepository.save(identityDocumentInformation);
  }

  @Override
  public Mono<IdentityDocumentInformation> findById(Long id) {
    return this.identifyDocumentInformationRepository.findById(id);
  }

  @Override
  public Flux<IdentityDocumentInformation> findAll(
      IdentificationDocumentInformationSpecification
          identificationDocumentInformationSpecification) {
    return this.r2dbcEntityTemplate.select(
        identificationDocumentInformationSpecification.getQuery(),
        IdentityDocumentInformation.class);
  }

  @Override
  public Mono<IdentityDocumentInformation> findByUserId(Long userId) {
    return this.identifyDocumentInformationRepository.findByUserId(userId);
  }
}
