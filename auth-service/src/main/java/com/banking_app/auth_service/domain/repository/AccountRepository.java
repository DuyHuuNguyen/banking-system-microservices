package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.account.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {
  Mono<Account> findAccountByPersonalIdentificationNumber(String personalIdentificationNumber);
}
