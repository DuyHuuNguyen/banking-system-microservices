package com.banking_app.auth_service.application.service;

public interface JwtService {
  String generateAccessToken(String personalIdentificationNumber);

  String generateRefreshToken(String personalIdentificationNumber);

  Boolean validateToken(String token);

  String getPersonalIdentificationNumberFromJwtToken(String token);

  String generateResetPasswordToken(String personalIdentificationNumber);
}
