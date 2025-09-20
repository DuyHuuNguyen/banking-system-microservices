package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.Getter;

@Getter
public class InternalCommunicationServiceException extends RuntimeException {
  private final String message;

  public InternalCommunicationServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.message = errorCode.getMessage();
  }
}
