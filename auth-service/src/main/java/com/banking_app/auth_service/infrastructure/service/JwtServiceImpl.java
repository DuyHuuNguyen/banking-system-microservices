package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.JwtService;
import com.example.enums.ErrorCode;
import com.example.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
  @Value("${jwt.secretKey}")
  private String SECRET_KEY;

  @Value("${jwt.accessTokenExpirationTime}")
  private String ACCESS_TOKEN_EXPIRATION_TIME;

  @Value("${jwt.refreshTokenExpirationTime}")
  private String REFRESH_TOKEN_EXPIRATION_TIME;

  @Value("${jwt.resetPasswordTokenExpirationTime}")
  private String RESET_PASSWORD_TOKEN_EXPIRATION_TIME;

  @Override
  public String generateAccessToken(String personalIdentificationNumber) {
    return Jwts.builder()
        .setSubject(personalIdentificationNumber)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + Long.parseLong(ACCESS_TOKEN_EXPIRATION_TIME)))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  @Override
  public String generateRefreshToken(String personalIdentificationNumber) {
    return Jwts.builder()
        .setSubject(personalIdentificationNumber)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME)))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  @Override
  public String generateResetPasswordToken(String personalIdentificationNumber) {
    return Jwts.builder()
        .setSubject(personalIdentificationNumber)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(
                System.currentTimeMillis() + Long.parseLong(RESET_PASSWORD_TOKEN_EXPIRATION_TIME)))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  @Override
  public Boolean validateToken(String token) {
    if (null == token) return false;
    try {
      Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parse(token);
    } catch (MalformedJwtException
        | ExpiredJwtException
        | UnsupportedJwtException
        | IllegalArgumentException exception) {
      throw new InvalidTokenException(ErrorCode.JWT_INVALID);
    }
    return true;
  }

  @Override
  public String getPersonalIdentificationNumberFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .get("sub", String.class);
  }
}
