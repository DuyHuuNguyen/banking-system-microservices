package com.example.base;

import com.example.enums.RoleEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AccountResponse {
  private Long id;
  private String email;
  private String phone;
  private String personalIdentificationNumber;
  private Long userId;
  private List<RoleEnum> roles;
}
