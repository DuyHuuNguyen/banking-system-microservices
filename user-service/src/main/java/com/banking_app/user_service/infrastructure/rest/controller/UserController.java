package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.UserFacade;
import com.banking_app.user_service.api.request.UpsertUserRequest;
import com.banking_app.user_service.api.response.ProfileResponse;
import com.banking_app.user_service.api.response.UserDetailResponse;
import com.example.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserFacade userFacade;

  @PostMapping("sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Users APIs"})
  public Mono<BaseResponse<Void>> signUp(@RequestBody @Valid UpsertUserRequest upsertUserRequest) {
    return this.userFacade.signUp(upsertUserRequest);
  }

  @GetMapping("/profile")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Users APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<ProfileResponse>> findProfile() {
    return this.userFacade.getProfile();
  }

  @GetMapping("/detail/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Users APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public Mono<BaseResponse<UserDetailResponse>> findDetailById(@PathVariable Long id) {
    return this.userFacade.findDetailById(id);
  }

  @GetMapping("/demo")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<BaseResponse<Void>> ok(@RequestParam String hehe) {
    return Mono.just(BaseResponse.ok());
  }
}
