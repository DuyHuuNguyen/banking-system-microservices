package com.banking_app.auth_service.infrastructure.rest.controller;

import com.banking_app.auth_service.api.facade.AuthFacade;
import com.banking_app.auth_service.api.request.LoginRequest;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.example.base.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {
  private final AuthFacade authFacade;

  @PostMapping("/login")
  public Mono<BaseResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
    return this.authFacade.login(loginRequest);
  }
}
