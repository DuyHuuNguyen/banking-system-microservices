package com.banking_app.wallet_service.domain.entity.wallet;

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
@Table("wallets")
public class Wallet extends BaseEntity {
  @Column("balance")
  private Double balance;

  @Column("currency")
  private String currency;

  @Column("user_id")
  private Long userId;

  @Column("wallet_detail_id")
  private Long walletDetailId;
}
