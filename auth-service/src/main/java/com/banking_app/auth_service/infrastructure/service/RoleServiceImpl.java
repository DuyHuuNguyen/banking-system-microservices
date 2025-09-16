package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.role.Role;
import com.banking_app.auth_service.domain.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Flux<Role> findRolesByAccountId(Long accountId) {
    return this.roleRepository.findRolesByAccountId(accountId);
  }

  @Override
  public Mono<Role> save(Role role) {
    return this.roleRepository.save(role);
  }

  @Override
  public Flux<Role> findByIdsIn(List<Long> ids) {
    return this.roleRepository.findByIdIsIn(ids);
  }
}
