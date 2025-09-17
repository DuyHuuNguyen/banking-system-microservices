package com.banking_app.auth_service.api.request;

import com.example.base.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCriteria extends BaseCriteria {
  private String phone;
  private String email;
  private String personalId;
}
