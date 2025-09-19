package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.UserFacade;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserFacade userFacade;

  @GetMapping("/demo")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<BaseResponse<Void>> ok() {
    return Mono.just(BaseResponse.ok());
  }
}
