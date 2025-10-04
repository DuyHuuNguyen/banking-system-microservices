package com.banking_app.user_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PersonalInformationWithLocationDTO {
  private PersonalInformationDTO personalInformationDTO;
  private LocationDTO locationDTO;
}
