package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.PersonalInformationService;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import com.banking_app.user_service.domain.repository.PersonalInformationRepository;
import com.banking_app.user_service.infrastructure.util.PersonalInformationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {
  private final PersonalInformationRepository personalInformationRepository;
  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<PersonalInformation> save(PersonalInformation personalInformation) {
    return this.personalInformationRepository.save(personalInformation);
  }

  @Override
  public Mono<PersonalInformation> findById(Long id) {
    return this.personalInformationRepository.findById(id);
  }

  @Override
  public Mono<PersonalInformation> findByUserId(Long userId) {
    return this.personalInformationRepository.findByUserId(userId);
  }

  @Override
  public Flux<PersonalInformation> findAll(
      PersonalInformationSpecification personalInformationSpecification) {
    return this.r2dbcEntityTemplate.select(
        personalInformationSpecification.getQuery(), PersonalInformation.class);
  }
}
