package com.banking_app.auth_service.domain.entity.account;

import com.banking_app.auth_service.domain.entity.common.BaseEntity;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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

  @Column("opt")
  private String otp;

  @Column("user_id")
  private Long userId;
}

@Repository
interface Abc extends ReactiveCrudRepository<Account, Long> {}

@Slf4j
@Service
@RequiredArgsConstructor
class test {
  private final Abc abc;

  @PostConstruct
  public void init() {
    log.info("init account");
    abc.save(
        Account.builder()
            .otp("090909")
            .email("duynguyenavg@gmail.com")
            .password("$2a$10$/ecarJ95FyD/K0hjT2fNt.8LWAAUKRMH96t9LkzY61q8ZCCWldjCW")
            .personalIdentificationNumber("abcksajdhgfljkfghjkldfsgljdfgljk")
            .phone("0398167244")
            .build());
  }
}
