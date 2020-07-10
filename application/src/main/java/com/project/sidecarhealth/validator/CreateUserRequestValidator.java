package com.project.sidecarhealth.validator;


import static com.project.sidecarhealth.constant.ErrorMessages.*;
import static com.project.sidecarhealth.constant.ParamNameConstants.*;
import static com.project.sidecarhealth.enumeration.ErrorCodes.INVALID_VALUE_CODE;
import static com.project.sidecarhealth.enumeration.ErrorCodes.EMPTY_REQUIRED_FIELD_CODE;
import static org.springframework.validation.ValidationUtils.invokeValidator;
import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

import com.project.sidecarhealth.dto.CreateUserRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateUserRequestValidator implements Validator {

    private static final String VALID_EMAIL_REGEXP = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        rejectIfEmpty(errors, USER_NAME, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_NAME_MESSAGE);
        rejectIfEmpty(errors, USER_LAST_NAME, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_LAST_NAME_MESSAGE);
        rejectIfEmpty(errors, USER_EMAIL, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_EMAIL_MESSAGE);
        rejectIfEmpty(errors, USER_API_KEY, EMPTY_REQUIRED_FIELD_CODE.getCode(), MISSING_USER_API_KEY_MESSAGE);

        final CreateUserRequestDTO createUserRequestDTO = (CreateUserRequestDTO) target;
        if (createUserRequestDTO.getEmail() != null && !createUserRequestDTO.getEmail().matches(VALID_EMAIL_REGEXP)) {
            errors.rejectValue(USER_EMAIL, INVALID_VALUE_CODE.getCode(), INVALID_USER_EMAIL_MESSAGE);
        }

    }
}
