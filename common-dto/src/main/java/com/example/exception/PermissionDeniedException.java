package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PermissionDeniedException extends RuntimeException {
  private final String message;

  private PermissionDeniedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.message = errorCode.getMessage();
  }
}
