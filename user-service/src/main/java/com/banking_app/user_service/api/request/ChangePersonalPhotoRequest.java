package com.banking_app.user_service.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePersonalPhotoRequest {
  private String personalPhoto;
}
