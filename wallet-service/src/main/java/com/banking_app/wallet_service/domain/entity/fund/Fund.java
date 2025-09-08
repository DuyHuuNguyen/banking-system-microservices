package com.banking_app.wallet_service.domain.entity.fund;

import com.banking_app.wallet_service.domain.entity.common.BaseEntity;
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
@Table("funds")
public class Fund extends BaseEntity {
  @Column("fake_balance")
  private Double fakeBalance;

  @Column("fund_name")
  private String fundName;

  @Column("description")
  private String description;

  @Column("wallet_id")
  private Long walletId;
}
