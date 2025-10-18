package com.banking_app.transaction_service.domain.entity.transaction;

import com.banking_app.transaction_service.domain.entity.common.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class Transaction extends BaseEntity {

  @Column("transaction_balance")
  private Double transactionBalance;

  @Column("transaction_currency")
  private String transactionCurrency;

  @Column("transaction_status")
  private String transactionStatus;

  @Column("transaction_type")
  private String transactionType;

  @Column("reference_code")
  private String referenceCode;

  @Column("originator_information_id")
  private Long originatorInformationId;

  @Column("beneficiary_information_id")
  private Long beneficiaryInformationId;
}
