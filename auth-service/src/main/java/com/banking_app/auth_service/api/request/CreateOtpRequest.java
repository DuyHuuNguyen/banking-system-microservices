package com.banking_app.auth_service.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@NotNull
@ToString
public class CreateOtpRequest {
  private String opt;
  private String otpConfirm;
}
