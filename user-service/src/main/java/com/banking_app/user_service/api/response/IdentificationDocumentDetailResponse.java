package com.banking_app.user_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IdentificationDocumentDetailResponse {
    private Long id;
    private String personalId;
    private Long issuedAt;
    private String citizenIdFront;
    private String citizenIdBack;
    private Long locationIssuePlaceId;
    private String country;
    private String province;
    private String district;
    private String ward;
    private String street;
    private String homesNumber;
}
