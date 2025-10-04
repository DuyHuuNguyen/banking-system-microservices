package com.banking_app.user_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PersonalInformationDetailResponse {
  private Long id;
  private String fullName;
  private Long dateOfBirth;
  private String sex;
  private String personalPhoto;
  private Long locationUserDetailId;
  private String country;
  private String province;
  private String district;
  private String ward;
  private String street;
  private String homesNumber;
}
