package com.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenTemplate {
  ACCESS_TOKEN("ACCESS_TOKEN_%s"),
  REFRESH_TOKEN("REFRESH_TOKEN_%s");
  private final String content;
}
