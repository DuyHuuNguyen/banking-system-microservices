package com.banking_app.auth_service.api.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangeActiveRequest {

  @Hidden private Long accountId;
  private Boolean isActive;

  public void withId(Long id) {
    this.accountId = id;
  }
}
