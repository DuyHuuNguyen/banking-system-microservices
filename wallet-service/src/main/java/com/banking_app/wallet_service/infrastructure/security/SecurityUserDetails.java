package com.banking_app.wallet_service.infrastructure.security;

import com.example.dto.AccountDTO;
import com.example.dto.AccountWithRoleDTO;
import com.example.server.grpc.AuthResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Builder
@Getter
public class SecurityUserDetails implements UserDetails {
   
    private Long accountId;

    private Long userId;

    private String email;

    private String phone;

    private String otp;

    private String personalIdentificationNumber;

    private Boolean isActive;

    private Collection<? extends GrantedAuthority> authorities;

    public static SecurityUserDetails build(AccountWithRoleDTO accountWithRoleDTO) {
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

    public static SecurityUserDetails build(AuthResponse authResponse) {
        return SecurityUserDetails.builder()
                .accountId(authResponse.getAccountId())
                .userId(authResponse.getUserId())
                .email(authResponse.getEmail())
                .phone(authResponse.getPhone())
                .otp(authResponse.getOtp())
                .personalIdentificationNumber(authResponse.getPersonalId())
                .isActive(authResponse.getIsActive())
                .authorities(authResponse.getRolesList().stream().map(SimpleGrantedAuthority::new).toList())
                .build();
    }
}
