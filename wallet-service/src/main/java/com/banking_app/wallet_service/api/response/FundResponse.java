package com.banking_app.wallet_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FundResponse {
  private Long id;
  private Double balance;
  private String fundName;
  private String description;
  private Long walletId;
}
