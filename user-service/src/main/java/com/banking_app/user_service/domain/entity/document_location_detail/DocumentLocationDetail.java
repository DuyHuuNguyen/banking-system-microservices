package com.banking_app.user_service.domain.entity.document_location_detail;

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
@Table("document_location_details")
public class DocumentLocationDetail extends BaseEntity {

  @Column("contry")
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
