package com.project.sidecarhealth.validator;

import static com.project.sidecarhealth.constant.ErrorMessages.*;
import static com.project.sidecarhealth.constant.ParamNameConstants.POST_TITLE;
import static com.project.sidecarhealth.enumeration.ErrorCodes.INVALID_VALUE_CODE;

import com.project.sidecarhealth.dto.PostRequestDTO;
import org.springframework.validation.Errors;

public abstract class PostRequestValidator extends BaseValidator {

  @Override
  public boolean supports(Class<?> clazz) {
    return PostRequestDTO.class.equals(clazz);
  }

  protected void validateTitle(String title, Errors errors) {
    if (title != null && (title.isEmpty() || title.length() > 60)) {
      errors.rejectValue(POST_TITLE, INVALID_VALUE_CODE.getCode(), INVALID_POST_TITLE_MESSAGE);
    }
  }
}
