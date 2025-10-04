package com.banking_app.user_service.infrastructure.util;

import com.banking_app.user_service.application.dto.WithinDateRangeDTO;

public interface PersonalInformationSpecificationBuilder {

  PersonalInformationSpecification build();

  PersonalInformationSpecificationBuilder createdAt(Long createdAt);

  PersonalInformationSpecificationBuilder createdAtWithinRange(
      WithinDateRangeDTO withinDateRangeDTO);

  PersonalInformationSpecificationBuilder pageSize(Integer pageSize);

  PersonalInformationSpecificationBuilder sex(String sex);

  PersonalInformationSpecificationBuilder pageNumber(Integer pageNumber);
}
