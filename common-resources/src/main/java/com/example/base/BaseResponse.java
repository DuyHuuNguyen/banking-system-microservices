package com.example.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BaseResponse<T> {
  private boolean isSuccess;
  private T metadata;

  public static <T> BaseResponse<T> build(T data, boolean isSuccess) {
    return (BaseResponse<T>) BaseResponse.builder().isSuccess(isSuccess).metadata(data).build();
  }

  public static <T> BaseResponse<T> ok() {
    return (BaseResponse<T>) BaseResponse.builder().isSuccess(true).metadata("Success").build();
  }

  public static <T> BaseResponse<T> fail() {
    return (BaseResponse<T>) BaseResponse.builder().isSuccess(false).metadata("Unsuccess").build();
  }
}
