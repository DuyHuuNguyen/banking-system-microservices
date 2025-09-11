package com.banking_app.auth_service.infrastructure.config.filter_factory_method;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.JwtService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.infrastructure.rest.interceptor.AuthTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;

@Component
@RequiredArgsConstructor
public class AuthTokenInterceptorCreator implements FilterCreator {

  private final JwtService jwtService;
  private final AccountService accountService;
  private final RoleService roleService;

  @Override
  public WebFilter create() {
    return new AuthTokenInterceptor(this.jwtService, accountService, roleService);
  }
}
