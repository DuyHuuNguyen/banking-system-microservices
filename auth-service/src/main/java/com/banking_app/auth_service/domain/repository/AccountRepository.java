package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.account.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {
  Mono<Account> findAccountByPersonalIdentificationNumber(String personalIdentificationNumber);

  @Query("""
  UPDATE account acc
  SET acc.is_first_login = false
  AND acc.is_one_device =true
  WHERE acc.id =:id
  """)
  void updateFirstLoginAndOneDeviceById(Long id,Boolean isFirstLogin, Boolean isOneDevice);
}
