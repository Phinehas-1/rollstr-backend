package com.bigdecimal.springbootbackend4nextjs.shared.controller;

import com.bigdecimal.springbootbackend4nextjs.shared.dto.ApiResponse;
import com.bigdecimal.springbootbackend4nextjs.shared.exception.InvalidOperationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidOperationException.class)
  public ResponseEntity<?> handleInvalidOperation(
    InvalidOperationException exception
  ) {
    return ResponseEntity
        .badRequest()
        .body(new ApiResponse(false, null, exception.getMessage()));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handleAuthenticationException(
    AuthenticationException exception
  ) {
    return ResponseEntity
        .badRequest()
        .body(new ApiResponse(false, null, "Error because of invalid authentication details."));
  }
}
