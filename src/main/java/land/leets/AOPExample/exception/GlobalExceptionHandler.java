package land.leets.AOPExample.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UnAuthorizedException.class)
  public String handleUnAuthorizedException(UnAuthorizedException e) {
    return e.getMessage();
  }
}
