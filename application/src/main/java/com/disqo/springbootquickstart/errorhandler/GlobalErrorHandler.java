package com.disqo.springbootquickstart.errorhandler;

import com.disqo.springbootquickstart.dto.ErrorResponseDTO;
import com.disqo.springbootquickstart.exception.InvalidFormatException;
import com.disqo.springbootquickstart.exception.RecordNotFoundException;
import com.disqo.springbootquickstart.constant.Constants;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(-2)
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalErrorHandler.class);

    public GlobalErrorHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                              ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {

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
        final List<ErrorResponseDTO> errorResponseDTOList = new ArrayList<>();

        if (throwable instanceof RecordNotFoundException) {
            final ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setErrorCode(Constants.ERROR_CODE_RECORD_NOT_FOUND);
            errorResponseDTO.setMessage(throwable.getMessage());
            errorResponseDTOList.add(errorResponseDTO);
            logger.info(errorResponseDTO.getMessage());

            return ServerResponse.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON )
                    .body(BodyInserters.fromValue(errorResponseDTOList));
        }

        if (throwable instanceof InvalidFormatException) {
            final ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setErrorCode(Constants.ERROR_CODE_INVALID_FIELD_VALUE);
            errorResponseDTO.setMessage(throwable.getMessage());
            errorResponseDTOList.add(errorResponseDTO);
            logger.info(errorResponseDTO.getMessage());

            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(errorResponseDTOList));
        }

        if (throwable instanceof ResponseStatusException) {
            final ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setErrorCode(Constants.ERROR_CODE_RECORD_NOT_FOUND);
            errorResponseDTO.setMessage(Constants.ERROR_MESSAGE_ENDPOINT_NOT_FOUND);
            errorResponseDTOList.add(errorResponseDTO);
            logger.info("Not Found: '{}'", errorResponseDTO.getMessage());

            return ServerResponse.status(HttpStatus.NOT_FOUND)
              .contentType(MediaType.APPLICATION_JSON)
              .body(BodyInserters.fromValue(errorResponseDTOList));
        }

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
