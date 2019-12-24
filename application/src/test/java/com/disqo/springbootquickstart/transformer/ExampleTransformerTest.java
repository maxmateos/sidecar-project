package com.disqo.springbootquickstart.transformer;

import com.disqo.springbootquickstart.dto.ExampleDTO;
import com.disqo.springbootquickstart.entity.Example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExampleTransformerTest {

  private ExampleTransformer subject;

  private static final Long PARAM_1 = 101L;
  private static final String PARAM_2 = "param2";

  @BeforeEach
  void setUp() {
    subject = new ExampleTransformer();
  }

  @Test
  void transformExampleToExampleDto_whenValidAttributes_shouldReturnExampleDto() {

    final Example example = buildExample(PARAM_1, PARAM_2);
    final ExampleDTO response = subject.transform(example);

    assertThat(response.getParam1()).isEqualTo(PARAM_1);
    assertThat(response.getParam2()).isEqualTo(PARAM_2);
    assertThat(response.getBothParams()).isEqualTo(String.join("-", PARAM_1.toString(), PARAM_2));

  }

  @Test
  void transformExampleToExampleDto_whenNullAttributes_shouldReturnExampleDto() {

    final Example example = new Example();
    final ExampleDTO response = subject.transform(example);

    assertThat(response.getParam1()).isNull();
    assertThat(response.getParam2()).isNull();
    assertThat(response.getBothParams()).isNull();

  }

  @Test
  void transformExampleToExampleDto_whenParam1IsNull_shouldReturnExampleDto() {

    final Example example = new Example();
    example.setParam2(PARAM_2);
    final ExampleDTO response = subject.transform(example);

    assertThat(response.getParam1()).isNull();
    assertThat(response.getParam2()).isEqualTo(PARAM_2);
    assertThat(response.getBothParams()).isNull();

  }

  @Test
  void transformExampleToExampleDto_whenParam2IsNull_shouldReturnExampleDto() {

    final Example example = new Example();
    example.setParam1(PARAM_1);
    final ExampleDTO response = subject.transform(example);

    assertThat(response.getParam1()).isEqualTo(PARAM_1);
    assertThat(response.getParam2()).isNull();
    assertThat(response.getBothParams()).isNull();

  }

  private Example buildExample(Long param1, String param2) {

    final Example example = new Example();
    example.setParam1(param1);
    example.setParam2(param2);

    return example;
  }
}