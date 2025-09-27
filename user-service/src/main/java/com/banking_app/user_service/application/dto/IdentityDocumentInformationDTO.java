package com.banking_app.user_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class IdentityDocumentInformationDTO {
  private String personalIdentificationNumber;

  private Long issuedAt;

  private Long locationIssuePlaceId;

  private String citizenIdFront;

  private String citizenIdBack;
}
