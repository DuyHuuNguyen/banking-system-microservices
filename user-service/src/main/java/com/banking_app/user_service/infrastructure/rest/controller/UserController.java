package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.UserFacade;
import com.banking_app.user_service.application.service.AuthGrpcClientService;
import com.example.base.BaseResponse;
import com.example.server.grpc.AuthResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserFacade userFacade;
  private final AuthGrpcClientService authGrpcClientService;

  @GetMapping("/demo")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<AuthResponse> ok(@RequestParam String hehe) {
    return authGrpcClientService.parseToken(hehe);
  }
}
