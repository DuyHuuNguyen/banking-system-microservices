package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationWithLocationDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertPersonalInformationRequest {
  @Hidden private Long id;
  @NotNull private PersonalInformationWithLocationDTO personalInformationWithLocationDTO;

  public void withId(Long id) {
    this.id = id;
  }

  @Hidden
  public PersonalInformationDTO getPersonalInformationDTO() {
    return personalInformationWithLocationDTO.getPersonalInformationDTO();
  }

  @Hidden
  public LocationDTO getLocationDTO() {
    return this.personalInformationWithLocationDTO.getLocationDTO();
  }
}
