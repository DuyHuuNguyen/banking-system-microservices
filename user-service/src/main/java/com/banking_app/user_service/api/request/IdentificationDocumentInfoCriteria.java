package com.banking_app.user_service.api.request;

import com.banking_app.user_service.application.dto.WithinDateRangeDTO;
import com.example.base.BaseCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationDocumentInfoCriteria extends BaseCriteria {
  private Long createdAt;
  private Long issuedAt;
  private WithinDateRangeDTO withinDateRangeDTO;
}
