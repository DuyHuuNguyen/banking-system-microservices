package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.application.dto.UserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserRequest {
  @NotNull private UserDTO userDTO;
  @NotNull private IdentityDocumentInformationDTO identityDocumentInformationDTO;
  @NotNull private LocationDTO locationOfIdentityDocumentInformationDTO;
  @NotNull private PersonalInformationDTO personalInformationDTO;
  @NotNull private LocationDTO locationOfPersonalInformationDTO;
}
