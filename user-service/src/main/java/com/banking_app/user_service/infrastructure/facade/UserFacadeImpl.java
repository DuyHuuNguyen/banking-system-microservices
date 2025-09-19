package com.banking_app.user_service.infrastructure.facade;

import com.banking_app.user_service.api.facade.UserFacade;
import com.banking_app.user_service.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
  private final AuthService authService;
}
