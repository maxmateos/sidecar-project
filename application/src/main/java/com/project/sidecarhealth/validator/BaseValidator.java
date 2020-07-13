package com.project.sidecarhealth.validator;

import org.springframework.validation.Validator;

public abstract class BaseValidator implements Validator {

  protected boolean isStringFieldEmpty(String field) {
    return field != null && field.isEmpty();
  }
}
