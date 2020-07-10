package com.project.sidecarhealth.dto;

import java.util.Objects;

public class ErrorResponseDTO {

  private String errorCode;
  private String message;

  public ErrorResponseDTO(String errorCode, String message) {

    this.errorCode = errorCode;
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorResponseDTO that = (ErrorResponseDTO) o;
    return Objects.equals(getErrorCode(), that.getErrorCode()) &&
            Objects.equals(getMessage(), that.getMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getErrorCode(), getMessage());
  }
}
