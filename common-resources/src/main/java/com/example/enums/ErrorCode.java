package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  ACCOUNT_NOT_FOUND("Account is not found"),
  JWT_INVALID("Jwt invalid"),
  ROLE_NOT_FOUND("Role is not found"),
  OTP_NONE_MATCH("Otp don't match"),
  REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
  LOGGED_IN("Cancel login by policy"),
  PASSWORD_INCORRECT("Password incorrect"),
  USER_NOT_FOUND("User is not found"),
  INFO_USER_INVALID("Info is invalid"),
  IDENTITY_DOCUMENT_NOT_FOUND("Identify document is not found"),
  LOCATION_NOT_FOUND("Location is not found"),
  PERSONAL_INFORMATION_NOT_FOUND("Personal information is not found");
  private final String message;
}
