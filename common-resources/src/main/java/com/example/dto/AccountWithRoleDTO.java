package com.example.dto;

import com.example.enums.RoleEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AccountWithRoleDTO {
  private Long accountId;
  private Long userId;
  private String email;
  private String phone;
  private String otp;
  private String personalIdentificationNumber;
  private Boolean isActive;
  @Builder.Default private List<RoleEnum> roleEnums = new ArrayList<>();

  @Builder.Default private Boolean isEnabled = false;
}
