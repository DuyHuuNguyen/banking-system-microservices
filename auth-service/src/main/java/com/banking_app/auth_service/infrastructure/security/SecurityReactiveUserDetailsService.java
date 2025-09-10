package com.banking_app.auth_service.infrastructure.security;

import com.banking_app.auth_service.application.service.AccountService;
import com.banking_app.auth_service.application.service.RoleService;
import com.banking_app.auth_service.domain.entity.account.Account;
import com.example.enums.ErrorCode;
import com.example.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityReactiveUserDetailsService implements ReactiveUserDetailsService {
  private final AccountService accountService;
  private final RoleService roleService;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return this.accountService
        .findByPersonalIdentificationNumber(username)
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND)))
        .flatMap(this::loadUserWithRoles);
  }

  private Mono<UserDetails> loadUserWithRoles(Account account) {
    return this.roleService
        .findRolesByAccountId(account.getId())
        .switchIfEmpty(Mono.error(new EntityNotFoundException(ErrorCode.ROLE_NOT_FOUND)))
        .map(role -> new SimpleGrantedAuthority(role.getRoleName().getContent()))
        .collectList()
        .map(simpleGrantedAuthority -> SecurityUserDetails.build(account, simpleGrantedAuthority));
  }
}
