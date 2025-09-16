package com.banking_app.auth_service.api.response;

import com.example.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RoleResponse {
  private RoleEnum roleName;
  private Long createdAt;
}
