package com.banking_app.user_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PersonalInformationDTO {

  private String fullName;

  private Long dateOfBirth;

  private String sex;

  private String personalPhoto;

  private Long locationUserDetailId;
}
