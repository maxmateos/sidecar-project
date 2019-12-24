package com.disqo.springbootquickstart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ErrorResponseDTO {

  @JsonProperty("errorCode")
  private String errorCode;

  @JsonProperty("message")
  private String message;

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
    return Objects.equals(errorCode, that.errorCode) &&
            Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorCode, message);
  }
}
