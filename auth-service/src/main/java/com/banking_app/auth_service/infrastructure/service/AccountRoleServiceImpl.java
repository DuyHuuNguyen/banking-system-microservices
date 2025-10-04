package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.AccountRoleService;
import com.banking_app.auth_service.domain.entity.account_role.AccountRole;
import com.banking_app.auth_service.domain.repository.RoleUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountRoleServiceImpl implements AccountRoleService {
  private final RoleUserRepository roleUserRepository;

  @Override
  public Mono<AccountRole> save(AccountRole accountRole) {
    accountRole.reUpdate();
    return this.roleUserRepository.save(accountRole);
  }

  @Override
  public Mono<Void> deleteAllAccountRoleByAccountId(Long accountId) {
    return this.roleUserRepository.deleteAllByAccountId(accountId);
  }
}
