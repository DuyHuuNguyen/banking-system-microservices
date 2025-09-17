package com.banking_app.auth_service.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@NotNull
public class ResetPasswordRequest {
  private String password;
  private String confirmPassword;
}
