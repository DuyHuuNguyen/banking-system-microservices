package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class PermissionDeniedException extends RuntimeException {
  private final String message;

  public PermissionDeniedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.message = errorCode.getMessage();
  }
}
