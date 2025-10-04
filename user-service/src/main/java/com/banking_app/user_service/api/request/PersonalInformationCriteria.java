package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.WithinDateRangeDTO;
import com.example.base.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalInformationCriteria extends BaseCriteria {
  private Long createdAt;
  private WithinDateRangeDTO withinDateRange;
  private String sex;
}
