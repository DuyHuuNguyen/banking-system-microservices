package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import com.banking_app.user_service.infrastructure.util.PersonalInformationSpecification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonalInformationService {
  Mono<PersonalInformation> save(PersonalInformation personalInformation);

  Mono<PersonalInformation> findById(Long id);

  Mono<PersonalInformation> findByUserId(Long userId);

  Flux<PersonalInformation> findAll(
      PersonalInformationSpecification personalInformationSpecification);
}
