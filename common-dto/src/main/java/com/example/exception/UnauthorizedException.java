package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
  private final String message;

  public UnauthorizedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.message = errorCode.getMessage();
  }
}
