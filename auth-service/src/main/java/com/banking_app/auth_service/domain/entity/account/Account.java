package com.banking_app.auth_service.domain.entity.account;

import com.banking_app.auth_service.domain.entity.common.BaseEntity;
import com.example.dto.AccountDTO;
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
@Table("accounts")
public class Account extends BaseEntity {

  @Column("email")
  private String email;

  @Column("password")
  private String password;

  @Column("phone")
  private String phone;

  @Column("personal_identification_number")
  private String personalIdentificationNumber;

  @Column("otp")
  private String otp;

  @Column("user_id")
  private Long userId;

  @Column("is_first_login")
  private Boolean isFirstLogin;

  @Column("is_one_device")
  private Boolean isOneDevice;

  public void isFirstLogin(Boolean isFirstLogin) {
    this.isFirstLogin = isFirstLogin;
  }

  public void isOneDevice(Boolean isOneDevice) {
    this.isOneDevice = isOneDevice;
  }

  public void changeInfo(AccountDTO accountDTO) {
    this.email = accountDTO.getEmail();
    this.userId = accountDTO.getUserId();
    this.password = accountDTO.getPasswordEncoded();
    this.phone = accountDTO.getPhone();
    this.otp = accountDTO.getOtp();
    this.personalIdentificationNumber = accountDTO.getPersonalIdentificationNumber();
    this.userId = accountDTO.getUserId();
    this.isFirstLogin = accountDTO.getIsFirstLogin();
    this.isOneDevice = accountDTO.getIsOneDevice();
  }

  public void updateOtp(String opt) {
    this.otp = opt;
  }
}
