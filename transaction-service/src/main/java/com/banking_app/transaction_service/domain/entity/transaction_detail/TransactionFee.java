package com.banking_app.transaction_service.domain.entity.transaction_detail;

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
@Table("transaction_fees")
public class TransactionFee extends BaseEntity {
  @Column("transaction_id")
  private Long transactionId;

  @Column("transaction_detail_id")
  private Long transactionDetailId;
}
