package com.banking_app.auth_service.infrastructure.rest.controller;

import com.banking_app.auth_service.api.facade.RoleFacade;
import com.banking_app.auth_service.api.request.UpsertRoleAccountRequest;
import com.banking_app.auth_service.api.request.UpsertRoleRequest;
import com.banking_app.auth_service.api.response.RoleResponse;
import com.example.base.BaseCriteria;
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

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Auths APIs"},
      summary = "Creating role by admin, if server response false,the role is created")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<Void>> createRole(
      @RequestBody @Valid UpsertRoleRequest upsertRoleRequest) {
    return this.roleFacade.createRole(upsertRoleRequest);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"Auths APIs"},
      summary = "Creating role by admin, if server response false,the role is created")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Mono<BaseResponse<PaginationResponse<RoleResponse>>> findAll(
      @NotNull BaseCriteria baseCriteria) {
    return this.roleFacade.findAll(baseCriteria);
  }
}
