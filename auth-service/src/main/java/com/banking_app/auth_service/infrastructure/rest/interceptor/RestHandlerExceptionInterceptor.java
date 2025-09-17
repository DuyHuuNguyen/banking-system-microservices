package com.banking_app.auth_service.infrastructure.rest.interceptor;

import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import java.net.URI;
import java.util.function.Consumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestHandlerExceptionInterceptor {

  @ExceptionHandler(EntityNotFoundException.class)
  public ProblemDetail handleException(EntityNotFoundException entityNotFoundException) {
    return build(
        HttpStatus.NOT_FOUND,
        entityNotFoundException,
        problem -> {
          problem.setType(URI.create("http://example.com/problems/"));
          problem.setTitle(entityNotFoundException.getMessage());
        });
  }

  @ExceptionHandler(PermissionDeniedException.class)
  public ProblemDetail handleException(PermissionDeniedException permissionDeniedException) {
    return build(
        HttpStatus.NOT_FOUND,
        permissionDeniedException,
        problem -> {
          problem.setType(URI.create("http://example.com/problems/"));
          problem.setTitle(permissionDeniedException.getMessage());
        });
  }

  private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
    var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    consumer.accept(problem);
    return problem;
  }
}
