package com.banking_app.user_service.infrastructure.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SecurityReactiveUserDetailService implements ReactiveUserDetailsService {
  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return null;
  }
}
