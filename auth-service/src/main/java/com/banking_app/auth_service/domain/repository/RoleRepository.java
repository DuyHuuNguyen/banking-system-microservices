package com.banking_app.auth_service.domain.repository;

import com.banking_app.auth_service.domain.entity.role.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {}
