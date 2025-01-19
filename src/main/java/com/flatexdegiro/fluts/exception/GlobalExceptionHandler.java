package com.flatexdegiro.fluts.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidInputException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidInputException(InvalidInputException ex) {
    Map<String, Object> errorResponse = Map.of(
        "timestamp", LocalDateTime.now(),
        "status", HttpStatus.BAD_REQUEST.value(),
        "error", "Bad Request",
        "message", ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    Map<String, Object> errorResponse = Map.of(
        "timestamp", LocalDateTime.now(),
        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "error", "Internal Server Error",
        "message", "An unexpected error occurred.",
        "details", ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
