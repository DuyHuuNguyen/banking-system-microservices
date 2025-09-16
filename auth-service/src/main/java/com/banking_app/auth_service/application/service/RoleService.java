package com.banking_app.auth_service.application.service;

import com.banking_app.auth_service.domain.entity.role.Role;
import com.example.enums.RoleEnum;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {
  Flux<Role> findRolesByAccountId(Long accountId);

  Mono<Role> save(Role role);

  Flux<Role> findByIdsIn(List<Long> ids);

  Mono<Role> findByRoleName(RoleEnum roleName);

  Flux<Role> findAll(Integer pageNumber, Integer pageSize);
}
