package com.banking_app.user_service.infrastructure.rest.controller;

import com.banking_app.user_service.api.facade.UserFacade;
import com.banking_app.user_service.api.request.CreateUserRequest;
import com.banking_app.user_service.api.request.UpdateUserRequest;
import com.banking_app.user_service.api.request.UserCriteria;
import com.banking_app.user_service.api.response.ProfileResponse;
import com.banking_app.user_service.api.response.UserDetailResponse;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
  public Mono<BaseResponse<Void>> signUp(@RequestBody @Valid CreateUserRequest upsertUserRequest) {
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
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<UserDetailResponse>> findDetailById(@PathVariable Long id) {
    return this.userFacade.findDetailById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Users APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> updateUser(
      @PathVariable Long id, @RequestBody UpdateUserRequest upsertUserRequest) {
    upsertUserRequest.withId(id);
    return this.userFacade.updateUser(upsertUserRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(tags = {"Users APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')||hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<PaginationResponse<ProfileResponse>>> findByFilter(@NotNull UserCriteria userCriteria){
    return this.userFacade.findByFilter(userCriteria);
  }
}
