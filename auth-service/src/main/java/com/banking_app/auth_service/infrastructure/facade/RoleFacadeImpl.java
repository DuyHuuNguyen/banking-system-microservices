package com.banking_app.auth_service.infrastructure.facade;

import com.banking_app.auth_service.api.facade.RoleFacade;
import com.banking_app.auth_service.api.request.UpsertRoleAccountRequest;
import com.banking_app.auth_service.application.service.AccountRoleService;
import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.account_role.AccountRole;
import com.example.base.BaseResponse;
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
                log.info("update role to account {}",upsertRoleAccountRequest);
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
}
