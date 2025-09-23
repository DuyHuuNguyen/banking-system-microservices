package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.IdentifyDocumentInformationService;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import com.banking_app.user_service.domain.repository.IdentifyDocumentInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentifyDocumentInformationServiceImpl implements IdentifyDocumentInformationService {
  private final IdentifyDocumentInformationRepository identifyDocumentInformationRepository;

  @Override
  public Mono<IdentityDocumentInformation> save(
      IdentityDocumentInformation identityDocumentInformation) {
    return identifyDocumentInformationRepository.save(identityDocumentInformation);
  }
}
