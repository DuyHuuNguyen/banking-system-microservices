package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OtpTemplate {
  OTP_TEMPLATE("OTP_%s");
  private final String content;
}
