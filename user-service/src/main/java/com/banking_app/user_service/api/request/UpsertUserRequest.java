package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpsertUserRequest {
  @Hidden private Long id;
  private UserDTO userDTO;
  private IdentityDocumentInformationDTO identityDocumentInformationDTO;
  private LocationDTO locationOfIdentityDocumentInformationDTO;
  private PersonalInformationDTO personalInformationDTO;
  private LocationDTO locationOfPersonalInformationDTO;
}
