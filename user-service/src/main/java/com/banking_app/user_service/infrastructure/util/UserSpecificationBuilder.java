package com.banking_app.user_service.infrastructure.util;

public interface UserSpecificationBuilder {

  UserSpecification build();

  UserSpecificationBuilder createdAtInMonth(Long createdAt);

  UserSpecificationBuilder builder();

  UserSpecificationBuilder createdAt(Long createdAt);

  UserSpecificationBuilder sex(String sex);

  UserSpecificationBuilder pageSize(Integer pageSize);

  UserSpecificationBuilder pageNumber(Integer pageNumber);
}
