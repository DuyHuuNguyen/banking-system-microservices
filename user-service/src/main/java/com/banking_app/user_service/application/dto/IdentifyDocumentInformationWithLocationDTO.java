package com.banking_app.user_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IdentifyDocumentInformationWithLocationDTO {
  private IdentityDocumentInformationDTO identityDocumentInformationDTO;
  private LocationDTO locationDTO;

  public String getPersonalId() {
    return this.identityDocumentInformationDTO.getPersonalIdentificationNumber();
  }
}
