package com.banking_app.user_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonalInformationResponse {
  private Long id;
  private String fullName;
  private Long dateOfBirth;
  private String sex;
  private String personalPhoto;
  private Long locationUserDetailId;
}
