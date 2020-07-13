package com.project.sidecarhealth.validator;

import static com.project.sidecarhealth.constant.ErrorMessages.*;
import static com.project.sidecarhealth.constant.ParamNameConstants.*;
import static com.project.sidecarhealth.enumeration.ErrorCodes.INVALID_VALUE_CODE;

import com.project.sidecarhealth.dto.UserRequestDTO;
import org.springframework.validation.Errors;

public abstract class UserRequestValidator extends BaseValidator {

  protected static final String VALID_EMAIL_REGEXP =
      "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

  @Override
  public boolean supports(Class<?> clazz) {
    return UserRequestDTO.class.equals(clazz);
  }

  protected void validateEmail(String email, Errors errors) {
    if (email != null && !email.matches(VALID_EMAIL_REGEXP)) {
      errors.rejectValue(USER_EMAIL, INVALID_VALUE_CODE.getCode(), INVALID_USER_EMAIL_MESSAGE);
    }
  }
}
