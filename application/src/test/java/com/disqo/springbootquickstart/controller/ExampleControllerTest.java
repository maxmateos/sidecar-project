package com.disqo.springbootquickstart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.disqo.springbootquickstart.constant.Constants;
import com.disqo.springbootquickstart.dto.ExampleDTO;
import com.disqo.springbootquickstart.entity.Example;
import com.disqo.springbootquickstart.service.ExampleService;
import com.disqo.springbootquickstart.transformer.ExampleTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ExampleControllerTest {

  private ExampleController subject;

  private ExampleService exampleService;
  private ExampleTransformer exampleTransformer;

  private static final Long PARAM_1 = 123L;
  private static final String PARAM_2 = "param2";

  private static final String RAW_PARAM_1 = "123";


  @BeforeEach
  void setUp() {

    exampleService = mock(ExampleService.class);
    exampleTransformer = mock(ExampleTransformer.class);

    subject = new ExampleController(exampleService, exampleTransformer);
  }

  @Test
  void getExampleById_shouldCallExampleServiceAndExampleTransformer_returnsHttpOkStatus() {

    final ServerRequest serverRequest = mock(ServerRequest.class);
    when(serverRequest.pathVariable(Constants.PARAM_1_KEY)).thenReturn(RAW_PARAM_1);
    when(serverRequest.pathVariable(Constants.PARAM_2_KEY)).thenReturn(PARAM_2);

    when(exampleService.castParam1ToValidLong(RAW_PARAM_1)).thenReturn(PARAM_1);

    final Example expectedExample = buildExample(PARAM_1, PARAM_2);
    when(exampleService.getExampleData(PARAM_1, PARAM_2)).thenReturn(expectedExample);

    final ExampleDTO exampleDTO = buildExampleDto(expectedExample);
    when(exampleTransformer.transform(any(Example.class))).thenReturn(exampleDTO);

    final Mono<ServerResponse> responseMono = subject.getExampleEndpoint(serverRequest);
    StepVerifier.create(responseMono)
      .expectNextMatches(response -> {
        assertThat(response.headers().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        return true;
      })
      .verifyComplete();

    verify(exampleService).castParam1ToValidLong(eq(RAW_PARAM_1));
    verify(exampleService).getExampleData(eq(PARAM_1), eq(PARAM_2));
    verify(exampleTransformer).transform(expectedExample);
  }

  private Example buildExample(Long param1, String param2) {

    final Example example = new Example();
    example.setParam1(param1);
    example.setParam2(param2);

    return example;
  }

  private ExampleDTO buildExampleDto(Example example) {

    final ExampleDTO exampleDTO = new ExampleDTO();
    exampleDTO.setParam1(example.getParam1());
    exampleDTO.setParam2(example.getParam2());
    exampleDTO.setBothParams(String.join("-", example.getParam1().toString(), example.getParam2()));

    return exampleDTO;
  }

}