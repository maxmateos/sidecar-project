package com.disqo.springbootquickstart.repository.jooq;

import com.disqo.springbootquickstart.entity.Example;
import com.disqo.springbootquickstart.exception.RecordNotFoundException;
import com.disqo.springbootquickstart.repository.ExampleRepository;
import com.disqo.springbootquickstart.constant.Constants;
import com.disqo.springbootquickstart.constant.DbConstants;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.*;

@Component
public class ExampleRepositoryImpl implements ExampleRepository {

    private static final Table<?> EXAMPLE_TABLE = new TableImpl(DSL.name(DbConstants.EXAMPLE_TABLE_NAME));
    private static final Field<Long> EXAMPLE_COLUMN_1 = DSL.field(DbConstants.EXAMPLE_COLUMN_1_FIELD, Long.class);
    private static final Field<String> EXAMPLE_COLUMN_2 = DSL.field(DbConstants.EXAMPLE_COLUMN_2_FIELD, String.class);

    private final DSLContext dsl;

    @Autowired
    public ExampleRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Example getExampleData(Long param1, String param2) {

        final Record record = dsl.select(asterisk())
          .from(EXAMPLE_TABLE)
          .where(EXAMPLE_COLUMN_1.eq(param1))
          .and(EXAMPLE_COLUMN_2.eq(param2))
          .fetchOne();

        if (record == null) {
            throw new RecordNotFoundException(String.format(
              Constants.ERROR_MESSAGE_RECORD_NOT_FOUND, param1, param2));
        }
        return record.into(Example.class);
    }


}
