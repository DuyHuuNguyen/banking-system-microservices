package com.banking_app.user_service.infrastructure.mapper;

import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.application.mapper.PersonalInformationMapper;
import com.banking_app.user_service.domain.entity.personal_information.PersonalInformation;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationMapperImpl implements PersonalInformationMapper {
  @Override
  public PersonalInformation toPersonalInformation(PersonalInformationDTO personalInformationDTO) {
    return PersonalInformation.builder()
        .fullName(personalInformationDTO.getFullName())
        .dateOfBirth(personalInformationDTO.getDateOfBirth())
        .sex(personalInformationDTO.getSex())
        .personalPhoto(personalInformationDTO.getPersonalPhoto())
        .build();
  }

  @Override
  public PersonalInformationDTO toPersonalInformationDTO(PersonalInformation personalInformation) {
    return PersonalInformationDTO.builder()
        .fullName(personalInformation.getFullName())
        .dateOfBirth(personalInformation.getDateOfBirth())
        .sex(personalInformation.getSex())
        .personalPhoto(personalInformation.getPersonalPhoto())
        .build();
  }
}
