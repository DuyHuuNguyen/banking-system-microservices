package com.example.dto;

import com.example.enums.RoleEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountWithRoleDTO {
  private Long accountId;
  private Long userId;
  private String email;
  private String phone;
  private String otp;
  private String personalIdentificationNumber;
  private Boolean isOneDevice;
  private Boolean isFirstLogin;
  @Builder.Default private List<RoleEnum> roleEnums = new ArrayList<>();
}
