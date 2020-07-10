package com.project.sidecarhealth.controller;

import com.project.sidecarhealth.exception.ValidationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface BaseController {

  default void validator(Validator validator, Object dtoObject) {

    final Errors errors = new BeanPropertyBindingResult(dtoObject, dtoObject.getClass().getName());
    validator.validate(dtoObject, errors);

    if (!errors.getAllErrors().isEmpty()) {
      throw new ValidationException(errors.getAllErrors());
    }
  }
}
