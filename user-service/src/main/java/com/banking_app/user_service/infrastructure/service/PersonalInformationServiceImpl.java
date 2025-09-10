package com.banking_app.user_service.infrastructure.service;

import com.banking_app.user_service.application.service.PersonalInformationService;
import com.banking_app.user_service.domain.repository.PersonalInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalInformationServiceImpl implements PersonalInformationService {
  private final PersonalInformationRepository personalInformationRepository;
}
