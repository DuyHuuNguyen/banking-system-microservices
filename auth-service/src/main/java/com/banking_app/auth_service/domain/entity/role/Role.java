package com.banking_app.auth_service.domain.entity.role;

import com.banking_app.auth_service.domain.entity.common.BaseEntity;
import com.example.enums.RoleEnum;
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
@Table("roles")
public class Role extends BaseEntity {
  @Column("role_name")
  private RoleEnum roleName;
}
