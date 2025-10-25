package com.banking_app.transaction_service.application.dto;

import com.banking_app.transaction_service.application.enums.TransactionTypeEnums;
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
  private TransactionTypeEnums transactionTypeEnums;
  private Double transactionBalance;
  private Long TransactionMethodId;
}
