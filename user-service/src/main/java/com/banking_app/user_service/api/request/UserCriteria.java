package com.banking_app.user_service.api.request;

import com.example.base.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCriteria extends BaseCriteria {
  private Long createdAt;
  private Long monthCreatedAt;
}
