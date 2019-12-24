package com.disqo.springbootquickstart.errorhandler;

import com.disqo.springbootquickstart.exception.InvalidFormatException;
import com.disqo.springbootquickstart.exception.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
class GlobalErrorHandlerTest {

  private GlobalErrorHandler subject;

  private ErrorAttributes errorAttributes;
  private ResourceProperties resourceProperties;
  private ServerCodecConfigurer serverCodecConfigurer;

  @Autowired
  private ApplicationContext applicationContext;

  private ServerRequest serverRequest;

  private static final String ERROR_DEFAULT_MESSAGE_1 = "Default Message 1";

  @BeforeEach
  void setUp() {

    serverRequest = mock(ServerRequest.class);

    errorAttributes = mock(ErrorAttributes.class);
    resourceProperties = mock(ResourceProperties.class);
    serverCodecConfigurer = mock(ServerCodecConfigurer.class);

    subject = new GlobalErrorHandler(errorAttributes, resourceProperties, applicationContext, serverCodecConfigurer);
  }

  @Test
  void getRoutingFunction_whenErrorAttributesReturnsNullThrowable_shouldReturnError500() {

    final Mono<HandlerFunction<ServerResponse>> responseMono = subject.getRoutingFunction(errorAttributes)
      .route(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(serverResponse -> {
        final Mono<ServerResponse> handledResponseMono = serverResponse.handle(serverRequest);
        StepVerifier.create(handledResponseMono)
          .expectNextMatches(response -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            return true;
          })
          .verifyComplete();
        return true;
      })
      .verifyComplete();
    verify(errorAttributes).getError(serverRequest);
    verifyNoMoreInteractions(errorAttributes);
  }

  @Test
  void getRoutingFunction_whenErrorAttributesReturnsRecordNotFoundException_shouldReturnError404() {

    final RecordNotFoundException exception = mock(RecordNotFoundException.class);
    when(errorAttributes.getError(serverRequest)).thenReturn(exception);

    final Mono<HandlerFunction<ServerResponse>> responseMono = subject.getRoutingFunction(errorAttributes)
      .route(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(serverResponse -> {
        final Mono<ServerResponse> handledResponseMono = serverResponse.handle(serverRequest);
        StepVerifier.create(handledResponseMono)
          .expectNextMatches(response -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            return true;
          })
          .verifyComplete();
        return true;
      })
      .verifyComplete();
    verify(errorAttributes).getError(serverRequest);
    verify(exception).getMessage();
  }

  @Test
  void getRoutingFunction_whenErrorAttributesReturnsInvalidFormatException_shouldReturnError400() {

    final InvalidFormatException exception = mock(InvalidFormatException.class);
    when(exception.getMessage()).thenReturn(ERROR_DEFAULT_MESSAGE_1);
    when(errorAttributes.getError(serverRequest)).thenReturn(exception);

    final Mono<HandlerFunction<ServerResponse>> responseMono = subject.getRoutingFunction(errorAttributes)
      .route(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(serverResponse -> {
        final Mono<ServerResponse> handledResponseMono = serverResponse.handle(serverRequest);
        StepVerifier.create(handledResponseMono)
          .expectNextMatches(response -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            return true;
          })
          .verifyComplete();
        return true;
      })
      .verifyComplete();
    verify(errorAttributes).getError(serverRequest);
    verify(exception).getMessage();
  }

  @Test
  void getRoutingFunction_whenErrorAttributesReturnsResponseStatusException_shouldReturnError404() {

    final ResponseStatusException exception = mock(ResponseStatusException.class);
    when(exception.getMessage()).thenReturn(ERROR_DEFAULT_MESSAGE_1);
    when(errorAttributes.getError(serverRequest)).thenReturn(exception);

    final Mono<HandlerFunction<ServerResponse>> responseMono = subject.getRoutingFunction(errorAttributes)
      .route(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(serverResponse -> {
        final Mono<ServerResponse> handledResponseMono = serverResponse.handle(serverRequest);
        StepVerifier.create(handledResponseMono)
          .expectNextMatches(response -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            return true;
          })
          .verifyComplete();
        return true;
      })
      .verifyComplete();
    verify(errorAttributes).getError(serverRequest);
  }
}