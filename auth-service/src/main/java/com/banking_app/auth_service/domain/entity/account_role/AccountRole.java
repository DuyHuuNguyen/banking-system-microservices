package com.banking_app.auth_service.domain.entity.account_role;

import com.banking_app.auth_service.domain.entity.common.BaseEntity;
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
@Table("account_roles")
public class AccountRole extends BaseEntity {
  @Column("user_id")
  private Long userId;

  @Column("role_id")
  private Long roleId;
}
