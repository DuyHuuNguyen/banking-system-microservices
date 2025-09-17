package com.banking_app.auth_service.api.request;

import com.example.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertRoleRequest {
  private RoleEnum roleName;
}
