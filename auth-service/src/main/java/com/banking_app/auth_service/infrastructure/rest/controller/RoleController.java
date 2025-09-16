package com.banking_app.auth_service.infrastructure.rest.controller;

import com.banking_app.auth_service.api.facade.RoleFacade;
import com.banking_app.auth_service.api.request.UpsertRoleAccountRequest;
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
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
  private final RoleFacade roleFacade;

  @PatchMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Auths APIs"},
      summary = "change roles to account by admin")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> changeRoleAccount(
      @RequestBody @Valid UpsertRoleAccountRequest upsertRoleAccountRequest) {
    return this.roleFacade.changeRoleAccount(upsertRoleAccountRequest);
  }
}
