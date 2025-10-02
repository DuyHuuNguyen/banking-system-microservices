package com.banking_app.user_service.infrastructure.util;

public interface IdentificationDocumentInformationSpecificationBuilder {

  IdentificationDocumentInformationSpecificationBuilder createdAt(Long createdAt);

  IdentificationDocumentInformationSpecificationBuilder issuedAt(Long issuedAt);

  IdentificationDocumentInformationSpecificationBuilder builder();

  IdentificationDocumentInformationSpecificationBuilder pageNumber(Integer pageNumber);

  IdentificationDocumentInformationSpecificationBuilder pageSize(Integer pageSize);

  IdentificationDocumentInformationSpecificationBuilder createdAtWithinRange(Long from, Long to);

  IdentificationDocumentInformationSpecification build();
}
