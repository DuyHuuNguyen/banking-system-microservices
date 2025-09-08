package com.banking_app.wallet_service.domain.entity.wallet_detail;

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
@Table("wallet_details")
public class WalletDetail extends BaseEntity {

  @Column("wallet_name")
  private String walletName;

  @Column("description")
  private String description;
}
