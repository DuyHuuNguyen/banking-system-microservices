package com.banking_app.auth_service.infrastructure.service;

import com.banking_app.auth_service.application.service.RoleUserService;
import com.banking_app.auth_service.domain.repository.RoleUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleUserServiceImpl implements RoleUserService {
  private final RoleUserRepository roleUserRepository;
}
