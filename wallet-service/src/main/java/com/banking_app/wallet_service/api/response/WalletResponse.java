package com.banking_app.wallet_service.api.response;

import com.example.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class WalletResponse {
  private Long id;
  private Double balance;
  private CurrencyEnum currency;
  private Long userId;
  private String walletName;
  private String description;
}
