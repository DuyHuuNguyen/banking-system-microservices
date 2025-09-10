package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.account_role.AccountRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends ReactiveCrudRepository<AccountRole, Long> {}
