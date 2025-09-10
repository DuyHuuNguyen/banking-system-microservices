package com.banking_app.auth_service.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@NotNull
public class LoginRequest {
  private String personalIdentificationNumber;
  private String password;
}
