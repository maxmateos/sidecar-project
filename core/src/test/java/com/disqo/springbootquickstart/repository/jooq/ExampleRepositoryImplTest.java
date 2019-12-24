package com.disqo.springbootquickstart.repository.jooq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.disqo.springbootquickstart.entity.Example;
import com.disqo.springbootquickstart.exception.RecordNotFoundException;
import com.disqo.springbootquickstart.repository.ExampleRepository;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.TableLike;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExampleRepositoryImplTest {

  private ExampleRepository subject;

  private DSLContext dslContext;

  private static final String RAW_PARAM_1 = "100";

  private static final Long PARAM_1 = Long.valueOf(RAW_PARAM_1);
  private static final String PARAM_2 = "param2";

  @BeforeEach
  void setUp() {

    dslContext = mock(DSLContext.class, RETURNS_DEEP_STUBS);
    subject = new ExampleRepositoryImpl(dslContext);
  }

  @Test
  void getUserById_whenValidResponse_shouldReturnUserObject() {

    final Record record = mock(Record.class);
    when(dslContext.select(any(SelectFieldOrAsterisk.class))
      .from(any(TableLike.class))
      .where(any(Condition.class))
      .and(any(Condition.class))
      .fetchOne()).thenReturn(record);

    final Example expectedExample = buildExample(PARAM_1, PARAM_2);
    when(record.into(eq(Example.class))).thenReturn(expectedExample);

    final Example example = subject.getExampleData(PARAM_1, PARAM_2);
    verify(record).into(Example.class);
    assertThat(example).isEqualTo(example);
  }

  @Test
  void getUserById_whenNoResponse_shouldThrowRecordNotFoundException() {

    when(dslContext.select(any(SelectFieldOrAsterisk.class))
      .from(any(TableLike.class))
      .where(any(Condition.class))
      .and(any(Condition.class))
      .fetchOne()).thenReturn(null);

    assertThrows(RecordNotFoundException.class, () -> subject.getExampleData(PARAM_1, PARAM_2));
  }

  private Example buildExample(Long param1, String param2) {

    final Example user = new Example();
    user.setParam1(param1);
    user.setParam2(param2);

    return user;
  }

}