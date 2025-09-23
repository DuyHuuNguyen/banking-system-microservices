package com.banking_app.user_service.application.service;

import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import reactor.core.publisher.Mono;

public interface PersonalInformationService {
  Mono<PersonalInformation> save(PersonalInformation personalInformation);
}
