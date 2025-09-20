package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN"),
  EMPLOYEE("ROLE_EMPLOYEE");

  private final String content;
}
