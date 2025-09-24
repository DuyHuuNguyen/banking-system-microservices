package com.banking_app.user_service.api.response;

import com.banking_app.user_service.application.dto.IdentityDocumentInformationDTO;
import com.banking_app.user_service.application.dto.LocationDTO;
import com.banking_app.user_service.application.dto.PersonalInformationDTO;
import com.banking_app.user_service.application.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDetailResponse {
  private Long id;
  private UserDTO userDTO;
  private IdentityDocumentInformationDTO identityDocumentInformationDTO;
  private LocationDTO locationOfIdentityDocumentInformationDTO;
  private PersonalInformationDTO personalInformationDTO;
  private LocationDTO locationOfPersonalInformationDTO;
}
