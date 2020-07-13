package com.project.sidecarhealth.validator.implementation;

import static com.project.sidecarhealth.constant.ErrorMessages.*;
import static com.project.sidecarhealth.constant.ParamNameConstants.*;
import static com.project.sidecarhealth.enumeration.ErrorCodes.EMPTY_REQUIRED_FIELD_CODE;
import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

import com.project.sidecarhealth.dto.PostRequestDTO;
import com.project.sidecarhealth.validator.PostRequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CreatePostRequestValidator extends PostRequestValidator {

  @Override
  public void validate(Object target, Errors errors) {

    rejectIfEmpty(
        errors, POST_TITLE, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_POST_TITLE_MESSAGE);
    rejectIfEmpty(
        errors, POST_BODY, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_POST_BODY_MESSAGE);

    final PostRequestDTO postRequestDTO = (PostRequestDTO) target;
    if (!isStringFieldEmpty(postRequestDTO.getTitle())) {
      validateTitle(postRequestDTO.getTitle(), errors);
    }
  }
}
