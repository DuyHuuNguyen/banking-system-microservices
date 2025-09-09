package com.banking_app.user_service.infastructure.service;

import com.banking_app.user_service.application.service.UserService;
import com.banking_app.user_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
}
