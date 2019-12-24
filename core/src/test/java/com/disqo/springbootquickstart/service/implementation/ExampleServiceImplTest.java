package com.disqo.springbootquickstart.service.implementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.disqo.springbootquickstart.entity.Example;
import com.disqo.springbootquickstart.exception.InvalidFormatException;
import com.disqo.springbootquickstart.repository.ExampleRepository;
import com.disqo.springbootquickstart.service.ExampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExampleServiceImplTest {

  private ExampleService subject;

  private ExampleRepository exampleRepository;

  private static final Long PARAM_1 = 123L;
  private static final String PARAM_2 = "param2";

  private static final String RAW_PARAM_1 = "123";
  private static final String STRING_PARAM_1 = "abc";
  private static final String INVALID_PARAM_1 = "-123";

  @BeforeEach
  void setUp() {
    exampleRepository = mock(ExampleRepository.class);
    subject = new ExampleServiceImpl(exampleRepository);
  }

  @Test
  void getExampleById_shouldCallExampleRepository_returnsExample() {

    final Example expectedExample = buildExample(PARAM_1, PARAM_2);
    when(exampleRepository.getExampleData(PARAM_1, PARAM_2)).thenReturn(expectedExample);

    final Example example = subject.getExampleData(PARAM_1, PARAM_2);
    assertThat(example).isEqualToComparingFieldByField(expectedExample);
    verify(exampleRepository).getExampleData(PARAM_1, PARAM_2);
  }

  @Test
  void castParam1ToValidLong_whenValidParam1_shouldReturnLongValue() {

    final Long param1 = subject.castParam1ToValidLong(RAW_PARAM_1);
    assertThat(param1).isEqualTo(PARAM_1);
  }

  @Test
  void castParam1ToValidLong_whenNullParam1_shouldThrowInvalidFormatException() {
    assertThrows(InvalidFormatException.class, () -> subject.castParam1ToValidLong(null));
  }

  @Test
  void castParam1ToValidLong_whenEmptyParam1_shouldThrowInvalidFormatException() {
    assertThrows(InvalidFormatException.class, () -> subject.castParam1ToValidLong(""));
  }

  @Test
  void castParam1ToValidLong_whenStringParam1_shouldThrowInvalidFormatException() {
    assertThrows(InvalidFormatException.class, () -> subject.castParam1ToValidLong(STRING_PARAM_1));
  }

  @Test
  void castParam1ToValidLong_whenInvalidParam1_shouldThrowInvalidFormatException() {
    assertThrows(InvalidFormatException.class, () -> subject.castParam1ToValidLong(INVALID_PARAM_1));
  }

  private Example buildExample(Long param1, String param2) {

    final Example example = new Example();
    example.setParam1(param1);
    example.setParam2(param2);

    return example;
  }

}