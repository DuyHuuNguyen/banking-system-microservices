package com.banking_app.user_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IdentityDocumentInformationResponse {
  private Long id;
  private String personalId;
  private Long issuedAt;
  private String citizenIdFront;
  private String citizenIdBack;
  private Long locationIssuePlaceId;
}
