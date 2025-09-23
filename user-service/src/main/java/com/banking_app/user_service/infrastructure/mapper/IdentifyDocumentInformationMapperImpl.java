package com.banking_app.user_service.infrastructure.mapper;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.mapper.IdentifyDocumentInformationMapper;
import com.banking_app.user_service.domain.entity.identity_document_information.IdentityDocumentInformation;
import org.springframework.stereotype.Service;

@Service
public class IdentifyDocumentInformationMapperImpl implements IdentifyDocumentInformationMapper {

  @Override
  public IdentityDocumentInformation toIdentityDocumentInformation(
      IdentityDocumentInformationDTO identityDocumentInformationDTO) {
    return IdentityDocumentInformation.builder()
        .personalIdentificationNumber(
            identityDocumentInformationDTO.getPersonalIdentificationNumber())
        .issuedAt(identityDocumentInformationDTO.getIssuedAt())
        .locationIssuePlaceId(identityDocumentInformationDTO.getIssuePlace())
        .citizenIdFront(identityDocumentInformationDTO.getCitizenIdFront())
        .citizenIdBack(identityDocumentInformationDTO.getCitizenIdFront())
        .build();
  }

  @Override
  public IdentityDocumentInformationDTO toIdentityDocumentInformationDTO(
      IdentityDocumentInformation identityDocumentInformation) {
    return null;
  }
}
