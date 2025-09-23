package com.banking_app.user_service.domain.entity.user_location_detail;

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
@Table("user_location_details")
public class UserLocationDetail extends BaseEntity {

  @Column("country")
  private String country;

  @Column("province")
  private String province;

  @Column("district")
  private String district;

  @Column("ward")
  private String ward;

  @Column("street")
  private String street;

  @Column("home_number")
  private String homesNumber;
}
