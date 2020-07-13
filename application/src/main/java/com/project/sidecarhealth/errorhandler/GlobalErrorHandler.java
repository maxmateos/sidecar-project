package com.project.sidecarhealth.errorhandler;

import static com.project.sidecarhealth.constant.ErrorMessages.INVALID_ENDPOINT_MESSAGE;
import static com.project.sidecarhealth.constant.ErrorMessages.INVALID_JSON_BODY_MESSAGE;
import static com.project.sidecarhealth.enumeration.ErrorCodes.*;

import com.project.sidecarhealth.dto.ErrorResponseDTO;
import com.project.sidecarhealth.exception.RecordNotFoundException;
import com.project.sidecarhealth.exception.ResourceAlreadyExistException;
import com.project.sidecarhealth.exception.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalErrorHandler.class);
  private static final String BAD_REQUEST_LOG = "Bad request: '{}'";

  public GlobalErrorHandler(
      ErrorAttributes errorAttributes,
      ResourceProperties resourceProperties,
      ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer) {

    super(errorAttributes, resourceProperties, applicationContext);
    super.setMessageWriters(serverCodecConfigurer.getWriters());
    super.setMessageReaders(serverCodecConfigurer.getReaders());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

    final Throwable throwable = getError(request);
    return renderRuntimeExceptions(throwable);
  }

  private Mono<ServerResponse> renderRuntimeExceptions(Throwable throwable) {

    if (throwable instanceof RecordNotFoundException) {
      return buildStandardResponse(
          HttpStatus.NOT_FOUND, RECORD_NOT_FOUND_CODE.getCode(), throwable.getMessage());
    }

    if (throwable instanceof ResourceAlreadyExistException) {
      return buildStandardResponse(
          HttpStatus.CONFLICT, RECORD_NOT_FOUND_CODE.getCode(), throwable.getMessage());
    }

    if (throwable instanceof ServerWebInputException) {
      return buildStandardResponse(
          HttpStatus.BAD_REQUEST, EMPTY_REQUIRED_FIELD_CODE.getCode(), INVALID_JSON_BODY_MESSAGE);
    }

    if (throwable instanceof ResponseStatusException) {
      return buildStandardResponse(
          HttpStatus.NOT_FOUND, RECORD_NOT_FOUND_CODE.getCode(), INVALID_ENDPOINT_MESSAGE);
    }

    if (throwable instanceof ValidationException) {
      return renderValidationException((ValidationException) throwable);
    }

    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .build();
  }

  private Mono<ServerResponse> renderValidationException(ValidationException throwable) {

    final List<ErrorResponseDTO> errorResponseDTOList =
        throwable
            .getErrors()
            .stream()
            .map(objectError -> buildErrorResponseDTOFromFieldError((FieldError) objectError))
            .peek(errorResponseDTO -> logger.info(BAD_REQUEST_LOG, errorResponseDTO.getMessage()))
            .collect(Collectors.toList());

    return buildServerResponseWithErrorDTO(HttpStatus.BAD_REQUEST, errorResponseDTOList);
  }

  private ErrorResponseDTO buildErrorResponseDTOFromFieldError(FieldError fieldError) {
    return new ErrorResponseDTO(
        fieldError.getCode(), fieldError.getField(), fieldError.getDefaultMessage());
  }

  private Mono<ServerResponse> buildStandardResponse(
      HttpStatus httpStatus, String code, String message) {

    logger.info("{}: '{}'", httpStatus, message);
    final ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(code, null, message);
    return buildServerResponseWithErrorDTO(httpStatus, Collections.singletonList(errorResponseDTO));
  }

  private Mono<ServerResponse> buildServerResponseWithErrorDTO(
      HttpStatus httpStatus, List<ErrorResponseDTO> errorResponseDTOList) {
    return ServerResponse.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(errorResponseDTOList);
  }
}
