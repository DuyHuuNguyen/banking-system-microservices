package com.banking_app.user_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LocationDTO {
  private String country;

  private String province;

  private String district;

  private String ward;

  private String street;

  private String homesNumber;
}
