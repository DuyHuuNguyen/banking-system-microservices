package com.banking_app.user_service.domain.entity.identity_document_information;

import com.banking_app.user_service.domain.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("identity_document_information")
public class IdentityDocumentInformation extends BaseEntity {

  @Column("personal_identification_number")
  private String personalIdentificationNumber;

  @Column("issued_at")
  private Long issuedAt;

  @Column("issue_place")
  private Long issuePlace;

  @Column("citizen_id_front")
  private String citizenIdFront;

  @Column("citizen_id_back")
  private String citizenIdBack;

  @Column("document_location_detail_id")
  private Long documentLocationDetailId;
}
