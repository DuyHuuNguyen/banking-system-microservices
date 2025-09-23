package com.banking_app.user_service.application.mapper;

import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;

public interface PersonalInformationMapper {
  PersonalInformation toPersonalInformation(PersonalInformationDTO personalInformationDTO);
}
