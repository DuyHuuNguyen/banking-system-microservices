package com.banking_app.user_service.infrastructure.rest.interceptor;

import com.example.exception.EntityNotFoundException;
import com.example.exception.PermissionDeniedException;
import com.example.exception.UnauthorizedException;
import java.net.URI;
import java.util.function.Consumer;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
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

  @ExceptionHandler(UnauthorizedException.class)
  public ProblemDetail handleException(UnauthorizedException unauthorizedException) {
    log.error(unauthorizedException.getMessage());
    return build(
        HttpStatus.BAD_REQUEST,
        unauthorizedException,
        problem -> {
          problem.setType(URI.create("http://example.com/problems/"));
          problem.setTitle("hehe");
        });
  }

  private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
    var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    consumer.accept(problem);
    return problem;
  }
}
