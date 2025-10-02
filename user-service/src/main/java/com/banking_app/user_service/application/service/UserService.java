package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.user.User;
import com.banking_app.user_service.infrastructure.util.UserSpecification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
  Mono<User> save(User user);

  Mono<User> findById(Long id);

  Flux<User> findAll(UserSpecification userSpecification);

  Mono<User> findByIdentificationDocumentInformationId(Long identificationDocumentInformationId);
}
