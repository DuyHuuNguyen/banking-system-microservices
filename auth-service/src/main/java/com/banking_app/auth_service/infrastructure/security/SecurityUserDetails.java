package com.banking_app.auth_service.infrastructure.security;

import com.banking_app.auth_service.domain.entity.account.Account;
import com.example.enums.RoleEnum;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Log4j2
@Builder
public class SecurityUserDetails implements UserDetails {
  @Getter private Long accountId;

  @Getter private Long userId;

  private String password;

  @Getter private String email;

  private String phone;

  @Getter private String otp;

  @Getter private String personalIdentificationNumber;

  @Getter private Boolean isOneDevice;

  @Getter private Boolean isFirstLogin;

  @Getter private Collection<? extends GrantedAuthority> authorities;

  public static SecurityUserDetails build(Account account) {
    return SecurityUserDetails.builder()
        .accountId(account.getId())
        .userId(account.getUserId())
        .password(account.getPassword())
        .email(account.getEmail())
        .phone(account.getPhone())
        .otp(account.getOtp())
        .personalIdentificationNumber(account.getPersonalIdentificationNumber())
        .isFirstLogin(account.getIsFirstLogin())
        .isOneDevice(account.getIsOneDevice())
        .build();
  }

  public static SecurityUserDetails build(
      Account account, Collection<? extends GrantedAuthority> authorities) {
    return SecurityUserDetails.builder()
        .accountId(account.getId())
        .userId(account.getUserId())
        .password(account.getPassword())
        .email(account.getEmail())
        .phone(account.getPhone())
        .otp(account.getOtp())
        .personalIdentificationNumber(account.getPersonalIdentificationNumber())
        .isFirstLogin(account.getIsFirstLogin())
        .isOneDevice(account.getIsOneDevice())
        .authorities(authorities)
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

  public Boolean isLoggedIn() {
    return !this.isFirstLogin && !this.isOneDevice;
  }

  @Override
  public String getUsername() {
    return this.personalIdentificationNumber;
  }

  public boolean isValid() {
    boolean isAllow =
        this.authorities.stream()
            .anyMatch(
                role ->
                    RoleEnum.ADMIN.getContent().equals(role)
                        || RoleEnum.EMPLOYEE.getContent().equals(role));
    return (!this.isFirstLogin && !this.isOneDevice) || isAllow;
  }
}
