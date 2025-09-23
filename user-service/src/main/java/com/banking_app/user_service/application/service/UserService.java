package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.user.User;
import reactor.core.publisher.Mono;

public interface UserService {
  Mono<User> save(User user);

  Mono<User> findById(Long id);
}
