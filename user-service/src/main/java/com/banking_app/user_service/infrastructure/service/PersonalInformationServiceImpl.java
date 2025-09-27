package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.PersonalInformationService;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import com.banking_app.user_service.domain.repository.PersonalInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {
  private final PersonalInformationRepository personalInformationRepository;

  @Override
  public Mono<PersonalInformation> save(PersonalInformation personalInformation) {
    return this.personalInformationRepository.save(personalInformation);
  }

  @Override
  public Mono<PersonalInformation> findById(Long id) {
    return this.personalInformationRepository.findById(id);
  }
}
