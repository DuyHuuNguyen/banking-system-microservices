package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CacheException extends RuntimeException {
  private final String message;

  public CacheException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.message = errorCode.getMessage();
  }
}
