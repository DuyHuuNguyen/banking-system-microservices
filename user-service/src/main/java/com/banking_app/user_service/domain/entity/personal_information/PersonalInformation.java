package com.banking_app.user_service.domain.entity.personal_information;

import com.banking_app.user_service.application.dto.PersonalInformationDTO;
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
@Table("personal_information")
public class PersonalInformation extends BaseEntity {

  @Column("full_name")
  private String fullName;

  @Column("date_of_birth")
  private Long dateOfBirth;

  @Column("sex")
  private String sex;

  @Column("personal_photo")
  private String personalPhoto;

  @Column("user_location_detail_id")
  private Long locationUserDetailId;

  public void addLocationUserDetailId(Long locationUserDetailId) {
    this.locationUserDetailId = locationUserDetailId;
  }

  public void updateInfo(PersonalInformationDTO personalInformationDTO) {
    this.fullName = personalInformationDTO.getFullName();
    this.dateOfBirth = personalInformationDTO.getDateOfBirth();
    this.sex = personalInformationDTO.getSex();
    this.personalPhoto = personalInformationDTO.getPersonalPhoto();
  }
}
