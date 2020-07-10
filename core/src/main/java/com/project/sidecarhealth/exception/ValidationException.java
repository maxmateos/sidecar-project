package com.project.sidecarhealth.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationException extends RuntimeException {

  private List<ObjectError> errors;

  public ValidationException() {}

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(List<ObjectError> errors) {
    this.errors = errors;
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }

  public ValidationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public List<ObjectError> getErrors() {
    return errors;
  }
}
