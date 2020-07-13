package com.project.sidecarhealth.controller;

import static com.project.sidecarhealth.constant.ErrorMessages.INVALID_USER_ID_MESSAGE;

import com.project.sidecarhealth.exception.RecordNotFoundException;
import com.project.sidecarhealth.exception.ValidationException;
import org.slf4j.Logger;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;

public interface BaseController {

  Logger logger = org.slf4j.LoggerFactory.getLogger(BaseController.class);
  String REQUEST_PLACEHOLDER = "REQUEST: '{}'";

  default void validator(Validator validator, Object dtoObject) {

    final Errors errors = new BeanPropertyBindingResult(dtoObject, dtoObject.getClass().getName());
    validator.validate(dtoObject, errors);

    if (!errors.getAllErrors().isEmpty()) {
      throw new ValidationException(errors.getAllErrors());
    }
  }

  default Long getIdFromPath(ServerRequest request, String paramKey) {

    final String rawId = request.pathVariable(paramKey);
    try {
      return Long.valueOf(rawId);
    } catch (NumberFormatException e) {
      logger.error("{} '{}' failed numeric format validation", paramKey, rawId);
      throw new RecordNotFoundException(String.format(INVALID_USER_ID_MESSAGE, rawId));
    }
  }
}
