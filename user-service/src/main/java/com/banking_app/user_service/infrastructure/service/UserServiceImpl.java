package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.UserService;
import com.banking_app.user_service.domain.entity.user.User;
import com.banking_app.user_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service(value = "UserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public Mono<User> save(User user) {
    return this.userRepository.save(user);
  }
}
