package com.banking_app.transaction_service.domain.entity.transaction_method;

import com.banking_app.transaction_service.domain.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction_method")
public class TransactionMethod extends BaseEntity {
  @Column("transaction_id")
  private Long transactionId;

  @Column("payment_method_id")
  private Long paymentMethodId;
}
