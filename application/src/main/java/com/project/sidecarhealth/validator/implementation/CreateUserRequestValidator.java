package com.project.sidecarhealth.validator.implementation;

import static com.project.sidecarhealth.constant.ErrorMessages.*;
import static com.project.sidecarhealth.constant.ParamNameConstants.*;
import static com.project.sidecarhealth.enumeration.ErrorCodes.EMPTY_REQUIRED_FIELD_CODE;
import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

import com.project.sidecarhealth.dto.UserRequestDTO;
import com.project.sidecarhealth.validator.UserRequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CreateUserRequestValidator extends UserRequestValidator {

  @Override
  public void validate(Object target, Errors errors) {

    rejectIfEmpty(
        errors, USER_NAME, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_NAME_MESSAGE);
    rejectIfEmpty(
        errors,
        USER_LAST_NAME,
        EMPTY_REQUIRED_FIELD_CODE.getCode(),
        MISSING_USER_LAST_NAME_MESSAGE);
    rejectIfEmpty(
        errors, USER_EMAIL, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_EMAIL_MESSAGE);
    rejectIfEmpty(
        errors, USER_API_KEY, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_API_KEY_MESSAGE);

    final UserRequestDTO userRequestDTO = (UserRequestDTO) target;
    if (!isStringFieldEmpty(userRequestDTO.getEmail())) {
      validateEmail(userRequestDTO.getEmail(), errors);
    }
  }
}
