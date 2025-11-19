package com.banking_app.transaction_service.api.request;

import com.banking_app.transaction_service.application.enums.TransactionTypeEnums;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@NotNull
public class CreateTransactionRequest {
  private Long beneficiaryInformationId;
  private Long originatorInformationId;
  private TransactionTypeEnums transactionTypeEnums;
  private Double transactionBalance;
  private Long TransactionMethodId;
  private final String idempotencyKey = UUID.randomUUID().toString();
}
