package com.project.sidecarhealth.validator.implementation;

import static com.project.sidecarhealth.constant.ErrorMessages.*;
import static com.project.sidecarhealth.constant.ParamNameConstants.*;
import static com.project.sidecarhealth.enumeration.ErrorCodes.EMPTY_REQUIRED_FIELD_CODE;

import com.project.sidecarhealth.dto.UserRequestDTO;
import com.project.sidecarhealth.validator.UserRequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UpdateUserRequestValidator extends UserRequestValidator {

  @Override
  public void validate(Object target, Errors errors) {

    final UserRequestDTO userRequestDTO = (UserRequestDTO) target;

    if (isStringFieldEmpty(userRequestDTO.getName())) {
      errors.rejectValue(USER_NAME, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_NAME_MESSAGE);
    }

    if (isStringFieldEmpty(userRequestDTO.getLastName())) {
      errors.rejectValue(
          USER_LAST_NAME, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_LAST_NAME_MESSAGE);
    }

    if (isStringFieldEmpty(userRequestDTO.getApiKey())) {
      errors.rejectValue(
          USER_API_KEY, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_API_KEY_MESSAGE);
    }

    validateEmail(userRequestDTO.getEmail(), errors);
  }
}
