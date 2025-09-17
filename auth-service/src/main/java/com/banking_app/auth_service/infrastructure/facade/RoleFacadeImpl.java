package com.banking_app.auth_service.infrastructure.facade;

import com.banking_app.auth_service.api.facade.RoleFacade;
import com.banking_app.auth_service.api.request.UpsertRoleAccountRequest;
import com.banking_app.auth_service.api.request.UpsertRoleRequest;
import com.banking_app.auth_service.api.response.RoleResponse;
import com.banking_app.auth_service.application.service.AccountRoleService;
import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.account_role.AccountRole;
import com.banking_app.auth_service.domain.entity.role.Role;
import com.example.base.BaseCriteria;
import com.example.base.BaseResponse;
import com.example.base.PaginationResponse;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class RoleFacadeImpl implements RoleFacade {
  private final RoleService roleService;
  private final AccountRoleService accountRoleService;
  private final AccountService accountService;

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> changeRoleAccount(
      UpsertRoleAccountRequest upsertRoleAccountRequest) {
    return this.accountService
        .findById(upsertRoleAccountRequest.getAccountId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
        .flatMap(
            account -> {
              log.info("update role to account {}", upsertRoleAccountRequest);
              return this.roleService
                  .findByIdsIn(upsertRoleAccountRequest.getRoleIds())
                  .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
                  .collectList()
                  .flatMap(
                      roles -> {
                        return this.accountRoleService
                            .deleteAllAccountRoleByAccountId(account.getId())
                            .thenMany(
                                Flux.fromIterable(roles)
                                    .flatMap(
                                        role -> {
                                          AccountRole accountRole =
                                              AccountRole.builder()
                                                  .roleId(role.getId())
                                                  .accountId(account.getId())
                                                  .build();
                                          return this.accountRoleService.save(accountRole);
                                        }))
                            .then(Mono.just(BaseResponse.ok()));
                      });
            });
  }

  @Override
  @Transactional
  public Mono<BaseResponse<Void>> createRole(UpsertRoleRequest upsertRoleRequest) {
    return this.roleService
        .findByRoleName(upsertRoleRequest.getRoleName())
        .defaultIfEmpty(Role.builder().roleName(upsertRoleRequest.getRoleName()).build())
        .flatMap(
            role -> {
              var isNewRole = (role.getId() == null);
              if (isNewRole) return this.roleService.save(role).then(Mono.just(BaseResponse.ok()));
              return Mono.just(BaseResponse.fail());
            });
  }

  @Override
  public Mono<BaseResponse<PaginationResponse<RoleResponse>>> findAll(BaseCriteria baseCriteria) {

    return this.roleService
        .findAll(baseCriteria.getCurrentPage(), baseCriteria.getPageSize())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
        .map(
            role ->
                RoleResponse.builder()
                    .roleName(role.getRoleName())
                    .createdAt(role.getCreatedAt())
                    .build())
        .collectList()
        .flatMap(
            roleResponses ->
                Mono.just(
                    BaseResponse.build(
                        PaginationResponse.<RoleResponse>builder()
                            .data(roleResponses)
                            .pageSize(baseCriteria.getPageSize())
                            .currentPage(baseCriteria.getCurrentPage())
                            .build(),
                        true)));
  }
}
