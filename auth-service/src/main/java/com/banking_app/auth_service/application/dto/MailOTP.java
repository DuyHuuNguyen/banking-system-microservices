package com.banking_app.auth_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MailOTP {
  private String otp;
  private String to;
  private String subject;
}
