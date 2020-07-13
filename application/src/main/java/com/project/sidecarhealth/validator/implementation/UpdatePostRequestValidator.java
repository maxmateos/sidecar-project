package com.project.sidecarhealth.validator.implementation;

import static com.project.sidecarhealth.constant.ErrorMessages.MISSING_POST_BODY_MESSAGE;
import static com.project.sidecarhealth.constant.ParamNameConstants.POST_TITLE;
import static com.project.sidecarhealth.enumeration.ErrorCodes.EMPTY_REQUIRED_FIELD_CODE;

import com.project.sidecarhealth.dto.PostRequestDTO;
import com.project.sidecarhealth.validator.PostRequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UpdatePostRequestValidator extends PostRequestValidator {

  @Override
  public void validate(Object target, Errors errors) {

    final PostRequestDTO postRequestDTO = (PostRequestDTO) target;

    validateTitle(postRequestDTO.getTitle(), errors);
    if (isStringFieldEmpty(postRequestDTO.getPostBody())) {
      errors.rejectValue(
          POST_TITLE, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_POST_BODY_MESSAGE);
    }
  }
}
