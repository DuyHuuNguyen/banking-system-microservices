package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.IdentifyDocumentInformationWithLocationDTO;
import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.dto.LocationDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertIdentificationDocumentInformationRequest {
  @Hidden private Long id;

  @NotNull
  private IdentifyDocumentInformationWithLocationDTO identifyDocumentInformationWithLocationDTO;

  @Hidden
  public IdentityDocumentInformationDTO getIdentityDocumentInformationDTO() {
    return this.identifyDocumentInformationWithLocationDTO.getIdentityDocumentInformationDTO();
  }

  @Hidden
  public LocationDTO getLocationOfIdentifyDocumentInformationDTO() {
    return this.identifyDocumentInformationWithLocationDTO.getLocationDTO();
  }

  @Hidden
  public String getPersonalId() {
    return this.identifyDocumentInformationWithLocationDTO.getPersonalId();
  }

  public void withId(Long id) {
    this.id = id;
  }
}
