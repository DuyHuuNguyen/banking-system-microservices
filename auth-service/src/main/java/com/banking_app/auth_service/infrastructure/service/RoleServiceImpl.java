package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.role.Role;
import com.banking_app.auth_service.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Flux<Role> findRolesByAccountId(Long accountId) {
    return this.roleRepository.findRolesByAccountId(accountId);
  }
}
