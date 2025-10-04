package com.banking_app.user_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProfileResponse {
  private Long id;
  private String email;
  private String phone;
  private String fullName;
  private String sex;
  private String personalPhoto;
}
