package com.banking_app.user_service.application.mapper;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;

public interface IdentifyDocumentInformationMapper {
  IdentityDocumentInformation toIdentityDocumentInformation(
      IdentityDocumentInformationDTO identityDocumentInformationDTO);

  IdentityDocumentInformationDTO toIdentityDocumentInformationDTO(
      IdentityDocumentInformation identityDocumentInformation);
}
