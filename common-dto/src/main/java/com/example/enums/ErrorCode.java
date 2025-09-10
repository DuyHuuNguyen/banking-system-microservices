package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  ACCOUNT_NOT_FOUND("Account is not found"),
  JWT_INVALID("Jwt invalid"),
  ROLE_NOT_FOUND("Role is not found");
  private final String message;
}
