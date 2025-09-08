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
@Table("transaction_method_details")
public class TransactionMethodDetail extends BaseEntity {
  @Column("payment_method_name")
  private String paymentMethodName;
}
