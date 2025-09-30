package com.banking_app.auth_service.infrastructure.util;

import java.util.Random;

public class GenerateOTPUntil {
  public static String generateOTP() {
    Random random = new Random();
    int code = random.nextInt(99999);
    return String.format("%05d", code);
  }
}
