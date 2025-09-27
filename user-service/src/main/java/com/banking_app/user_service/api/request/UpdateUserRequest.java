package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.application.dto.UserDTO;
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
  @NotNull private IdentityDocumentInformationDTO identityDocumentInformationDTO;
  @NotNull private PersonalInformationDTO personalInformationDTO;

  public void withId(Long id) {
    this.id = id;
  }
}
