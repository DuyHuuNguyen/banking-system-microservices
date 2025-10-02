package com.banking_app.user_service.domain.entity.user_location_detail;

import com.banking_app.user_service.application.dto.LocationDTO;
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

  public void updateInfo(LocationDTO documentLocationDetailDTO) {
    this.country = documentLocationDetailDTO.getCountry();
    this.province = documentLocationDetailDTO.getProvince();
    this.district = documentLocationDetailDTO.getDistrict();
    this.ward = documentLocationDetailDTO.getWard();
    this.street = documentLocationDetailDTO.getStreet();
    this.homesNumber = documentLocationDetailDTO.getHomesNumber();
  }
}
