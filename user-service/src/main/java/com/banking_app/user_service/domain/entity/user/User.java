package com.banking_app.user_service.domain.entity.user;

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
@Table("uses")
public class User extends BaseEntity {
  @Column("email")
  private String email;

  @Column("phone")
  private String phone;

  @Column("is_valid_login")
  private Boolean isValidLogin;

  @Column("is_one_device")
  private Boolean isOneDevice;

  @Column("personal_information_id")
  private Long personalInformationId;

  @Column("identify_document_information_id")
  private Long identifyDocumentInformationId;

  @Column("user_location_detail_id")
  private Long userLocationDetailId;
}
