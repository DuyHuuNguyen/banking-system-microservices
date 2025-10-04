package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountDTO {
  private Long id;
  private Long userId;
  private String email;
  private String passwordEncoded;
  private String phone;
  private String otp;
  private String personalIdentificationNumber;
  private Boolean isActive;
}
