package com.banking_app.transaction_service.infastructure.security;

import com.example.dto.AccountWithRoleDTO;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
public class SecurityUserDetails implements UserDetails {
  @Getter private Long accountWithRoleDTOId;

  @Getter private Long userId;

  private String password;

  @Getter private String email;

  private String phone;

  @Getter private String otp;

  @Getter private String personalIdentificationNumber;

  @Getter private Boolean isOneDevice;

  @Getter private Boolean isFirstLogin;

  @Getter private Collection<? extends GrantedAuthority> authorities;

  public static SecurityUserDetails build(AccountWithRoleDTO accountWithRoleDTO) {
    return SecurityUserDetails.builder()
        .accountWithRoleDTOId(accountWithRoleDTO.getAccountId())
        .userId(accountWithRoleDTO.getUserId())
        .email(accountWithRoleDTO.getEmail())
        .phone(accountWithRoleDTO.getPhone())
        .otp(accountWithRoleDTO.getOtp())
        .personalIdentificationNumber(accountWithRoleDTO.getPersonalIdentificationNumber())
        .isFirstLogin(accountWithRoleDTO.getIsFirstLogin())
        .isOneDevice(accountWithRoleDTO.getIsOneDevice())
        .authorities(
            accountWithRoleDTO.getRoleEnums().stream()
                .map(roleEnum -> new SimpleGrantedAuthority(roleEnum.getContent()))
                .toList())
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.personalIdentificationNumber;
  }
}
