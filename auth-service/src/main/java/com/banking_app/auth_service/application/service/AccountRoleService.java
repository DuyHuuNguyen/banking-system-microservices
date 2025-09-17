package com.banking_app.auth_service.application.service;

import com.banking_app.auth_service.domain.entity.account_role.AccountRole;
import reactor.core.publisher.Mono;

public interface AccountRoleService {
  Mono<AccountRole> save(AccountRole accountRole);

  Mono<Void> deleteAllAccountRoleByAccountId(Long accountId);
}
