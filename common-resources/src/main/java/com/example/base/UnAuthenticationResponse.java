package com.example.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnAuthenticationResponse {
  private Integer statusCode;
  private String error;
  private String message;
}
