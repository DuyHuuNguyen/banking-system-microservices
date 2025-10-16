package com.banking_app.wallet_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FundDTO {
  private Long id;
  private Double balance;
  private String fundName;
  private String description;
}
