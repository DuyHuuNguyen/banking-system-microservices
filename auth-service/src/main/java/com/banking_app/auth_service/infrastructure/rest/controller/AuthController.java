package com.banking_app.auth_service.infrastructure.rest.controller;

import com.banking_app.auth_service.api.facade.AuthFacade;
import com.banking_app.auth_service.api.request.LoginRequest;
import com.banking_app.auth_service.api.request.RefreshTokenRequest;
import com.banking_app.auth_service.api.request.UpsertAccountRequest;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.banking_app.auth_service.api.response.RefreshTokenResponse;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {
  private final AuthFacade authFacade;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Auths APIs"},
      summary =
          "You just login one time, If you want to login again, you must go to the bank office")
  public Mono<BaseResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
    return this.authFacade.login(loginRequest);
  }

  @PostMapping("/refresh-token")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  public Mono<BaseResponse<RefreshTokenResponse>> refreshToken(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    return this.authFacade.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<BaseResponse<Void>> logout() {
    return this.authFacade.logout();
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
  public Mono<BaseResponse<Void>> changeInfoAccount(
      @PathVariable Long id, @RequestBody @Valid UpsertAccountRequest upsertAccountRequest) {
    upsertAccountRequest.withId(id);
    return this.authFacade.changeInfoAccount(upsertAccountRequest);
  }

  @GetMapping("/he")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<BaseResponse<Void>> get() {
    return Mono.just(BaseResponse.ok());
  }
}
