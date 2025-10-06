package com.banking_app.wallet_service.api.request;

import com.example.enums.CurrencyEnum;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertWalletRequest {
  @Hidden private Long id;
  @NotNull private CurrencyEnum currency;
  @NotNull private String walletName;
  private String description;

  public void withId(Long id) {
    this.id = id;
  }
}
