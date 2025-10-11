package com.banking_app.wallet_service.api.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertFundRequest {
  @Hidden private Long id;
  @NotNull private Long walletId;
  @NotNull private String fundName;
  private String description;

  public void withId(Long id) {
    this.id = id;
  }
}
