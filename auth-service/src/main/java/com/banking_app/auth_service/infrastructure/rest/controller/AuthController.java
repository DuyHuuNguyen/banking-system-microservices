package com.banking_app.auth_service.infrastructure.rest.controller;

import com.banking_app.auth_service.api.facade.AuthFacade;
import com.banking_app.auth_service.api.request.*;
import com.banking_app.auth_service.api.response.ForgotPasswordResponse;
import com.banking_app.auth_service.api.response.LoginResponse;
import com.banking_app.auth_service.api.response.RefreshTokenResponse;
import com.example.base.AccountResponse;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

  @PatchMapping("/active/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> changeActive(
      @PathVariable Long id, @RequestBody @Valid ChangeActiveRequest changeActiveRequest) {
    changeActiveRequest.withId(id);
    return this.authFacade.changeActive(changeActiveRequest);
  }

  @PatchMapping("/transaction-otp")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<BaseResponse<Void>> createOtp(@RequestBody CreateOtpRequest createOtpRequest) {
    return this.authFacade.createOtp(createOtpRequest);
  }

  @PostMapping("/verify-otp")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<Boolean>> isVerifyOtp(@RequestBody VerifyOptRequest verifyOptRequest) {
    return this.authFacade.isVerifyOtp(verifyOptRequest);
  }

  @PostMapping("/forgot-password")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  public Mono<BaseResponse<ForgotPasswordResponse>> forgotPassword(
      @RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
    return this.authFacade.forgotPassword(forgotPasswordRequest);
  }

  @PatchMapping("rest-password")
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<Void>> restPassword(
      @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
    return this.authFacade.resetPassword(resetPasswordRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(tags = {"Auths APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<PaginationResponse<AccountResponse>>> findByFilter(
      @NotNull AccountCriteria accountCriteria) {
    return this.authFacade.findByFilter(accountCriteria);
  }

  @Hidden
  @GetMapping(value = "/internal/{id}", headers = "secret-api-key=auth-23130075")
  @ResponseStatus(HttpStatus.OK)
  public Mono<AccountResponse> findById(@PathVariable Long id) {
    return this.authFacade.findById(id);
  }
}
