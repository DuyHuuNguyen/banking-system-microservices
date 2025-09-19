package com.banking_app.user_service.infrastructure.security;

import com.example.dto.AccountDTO;
import com.example.dto.AccountWithRoleDTO;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Log4j2
@Builder
public class SecurityUserDetails implements UserDetails {
  @Getter private Long accountId;

  @Getter private Long userId;

  @Getter private String email;

  private String phone;

  @Getter private String otp;

  @Getter private String personalIdentificationNumber;

  @Getter private Boolean isOneDevice;

  @Getter private Boolean isFirstLogin;

  @Getter private Boolean isActive;

  @Getter private Collection<? extends GrantedAuthority> authorities;

  public static SecurityUserDetails build(AccountWithRoleDTO accountWithRoleDTO) {
    //    var roles = ;
    return SecurityUserDetails.builder()
        .accountId(accountWithRoleDTO.getAccountId())
        .userId(accountWithRoleDTO.getUserId())
        .userId(accountWithRoleDTO.getUserId())
        .email(accountWithRoleDTO.getEmail())
        .phone(accountWithRoleDTO.getPhone())
        .otp(accountWithRoleDTO.getOtp())
        .personalIdentificationNumber(accountWithRoleDTO.getPersonalIdentificationNumber())
        .isActive(accountWithRoleDTO.getIsActive())
        .authorities(
            accountWithRoleDTO.getRoleEnums().stream()
                .map(roleEnum -> new SimpleGrantedAuthority(roleEnum.getContent()))
                .toList())
        .build();
  }

  public static SecurityUserDetails build(AccountDTO account) {
    return SecurityUserDetails.builder()
        .accountId(account.getId())
        .userId(account.getUserId())
        .email(account.getEmail())
        .phone(account.getPhone())
        .otp(account.getOtp())
        .personalIdentificationNumber(account.getPersonalIdentificationNumber())
        .isActive(account.getIsActive())
        .build();
  }

  public static SecurityUserDetails build(
      AccountDTO account, Collection<? extends GrantedAuthority> authorities) {
    return SecurityUserDetails.builder()
        .accountId(account.getId())
        .userId(account.getUserId())
        .email(account.getEmail())
        .phone(account.getPhone())
        .otp(account.getOtp())
        .personalIdentificationNumber(account.getPersonalIdentificationNumber())
        .authorities(authorities)
        .isActive(account.getIsActive())
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return this.personalIdentificationNumber;
  }
}
