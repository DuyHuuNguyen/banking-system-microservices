package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRequest {
  private Long id;
  @NotNull private UserDTO userDTO;
  @NotNull private IdentifyDocumentInformationWithLocationDTO identityDocumentInformationDTO;
  @NotNull private PersonalInformationWithLocationDTO personalInformationWithLocationDTO;

  public void withId(Long id) {
    this.id = id;
  }

  @Hidden
  public String getPersonalId() {
    return this.identityDocumentInformationDTO.getPersonalId();
  }
}
