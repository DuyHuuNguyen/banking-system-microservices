package com.banking_app.transaction_service.application.dto;

import com.banking_app.transaction_service.application.enums.TransactionTypeEnums;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateTransactionDTO {
  private Long userIdOfOriginator;
  private Long beneficiaryInformationId;
  private Long originatorInformationId;
  private String opt;
  private Boolean isWallet;
  private TransactionTypeEnums transactionTypeEnums;
  private Double transactionBalance;
  private Long TransactionMethodId;
  @Hidden @Builder.Default private Integer retryTimes = 0;

  public void plusRetryTimes() {
    this.retryTimes++;
  }
}
