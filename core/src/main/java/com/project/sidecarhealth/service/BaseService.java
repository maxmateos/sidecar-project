package com.project.sidecarhealth.service;

public interface BaseService {

  default <T> T setValueIfNotNull(T newValue, T originalValue) {
    return (newValue != null) ? newValue : originalValue;
  }
}
