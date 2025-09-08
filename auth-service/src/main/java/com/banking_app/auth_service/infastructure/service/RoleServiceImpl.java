package com.banking_app.auth_service.infastructure.service;

import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
}
