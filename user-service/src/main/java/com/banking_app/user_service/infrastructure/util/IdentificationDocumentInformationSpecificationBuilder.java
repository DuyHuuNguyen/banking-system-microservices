package com.banking_app.user_service.infrastructure.util;

import com.banking_app.user_service.application.dto.WithinDateRangeDTO;

public interface IdentificationDocumentInformationSpecificationBuilder {

  IdentificationDocumentInformationSpecificationBuilder createdAt(Long createdAt);

  IdentificationDocumentInformationSpecificationBuilder issuedAt(Long issuedAt);

  IdentificationDocumentInformationSpecificationBuilder builder();

  IdentificationDocumentInformationSpecificationBuilder pageNumber(Integer pageNumber);

  IdentificationDocumentInformationSpecificationBuilder pageSize(Integer pageSize);

  IdentificationDocumentInformationSpecificationBuilder createdAtWithinRange(Long from, Long to);

  IdentificationDocumentInformationSpecificationBuilder createdAtWithinRange(
      WithinDateRangeDTO dateRange);

  IdentificationDocumentInformationSpecification build();
}
