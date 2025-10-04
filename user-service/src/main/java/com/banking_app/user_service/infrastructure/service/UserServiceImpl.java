package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.UserService;
import com.banking_app.user_service.domain.entity.user.User;
import com.banking_app.user_service.domain.repository.UserRepository;
import com.banking_app.user_service.infrastructure.util.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service(value = "UserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<User> save(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public Mono<User> findById(Long id) {
    return this.userRepository.findById(id);
  }

  @Override
  public Flux<User> findAll(UserSpecification userSpecification) {
    return this.r2dbcEntityTemplate.select(userSpecification.getQuery(), User.class);
  }

  @Override
  public Mono<User> findByIdentificationDocumentInformationId(
      Long identificationDocumentInformationId) {
    return this.userRepository.findByIdentificationDocumentInformationId(
        identificationDocumentInformationId);
  }
}
