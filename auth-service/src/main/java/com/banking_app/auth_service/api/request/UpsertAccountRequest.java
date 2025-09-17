package com.banking_app.auth_service.api.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@NotNull
public class UpsertAccountRequest {
  @Hidden private Long id;
  @Email private String email;
  private String password;
  private String phone;
  private String personalIdentificationNumber;
  private String otp;
  private Long userId;

  public void withId(Long id) {
    this.id = id;
  }
}
