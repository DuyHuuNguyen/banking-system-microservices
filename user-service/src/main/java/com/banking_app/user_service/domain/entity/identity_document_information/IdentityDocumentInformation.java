package com.banking_app.user_service.domain.entity.identity_document_information;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.domain.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table("identity_document_information")
public class IdentityDocumentInformation extends BaseEntity {

  @Column("personal_identification_number")
  private String personalIdentificationNumber;

  @Column("issued_at")
  private Long issuedAt;

  @Column("location_issue_place_id")
  private Long locationIssuePlaceId;

  @Column("citizen_id_front")
  private String citizenIdFront;

  @Column("citizen_id_back")
  private String citizenIdBack;

  public void addLocationIssuePlaceId(Long documentLocationDetailId) {
    this.locationIssuePlaceId = documentLocationDetailId;
  }

  public void updateInfo(IdentityDocumentInformationDTO identityDocumentInformationDTO) {
    this.personalIdentificationNumber =
        identityDocumentInformationDTO.getPersonalIdentificationNumber();
    this.issuedAt = identityDocumentInformationDTO.getIssuedAt();
    this.citizenIdBack = identityDocumentInformationDTO.getCitizenIdBack();
    this.citizenIdFront = identityDocumentInformationDTO.getCitizenIdFront();
  }
}
