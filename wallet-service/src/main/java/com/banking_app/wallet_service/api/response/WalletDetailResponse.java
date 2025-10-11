package com.banking_app.wallet_service.api.response;

import com.banking_app.wallet_service.application.dto.FundDTO;
import com.example.enums.CurrencyEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WalletDetailResponse {
  private Long id;
  private Double balance;
  private CurrencyEnum currency;
  private Long userId;
  private String walletName;
  private String description;
  private List<FundDTO> fundDTOS;

  public void addFundDTOS(List<FundDTO> fundDTOS) {
    this.fundDTOS = fundDTOS;
  }
}
