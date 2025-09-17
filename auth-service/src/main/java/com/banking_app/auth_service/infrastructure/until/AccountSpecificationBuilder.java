package com.banking_app.auth_service.infrastructure.until;

public interface AccountSpecificationBuilder {
  AccountSpecification build();

  AccountSpecificationBuilder builder();

  AccountSpecificationBuilder phone(String phone);

  AccountSpecificationBuilder email(String email);

  AccountSpecificationBuilder personalId(String personalId);

  AccountSpecificationBuilder pageSize(Integer pageSize);

  AccountSpecificationBuilder pageNumber(Integer pageNumber);
}
