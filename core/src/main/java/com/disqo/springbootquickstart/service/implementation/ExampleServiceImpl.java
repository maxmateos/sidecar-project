package com.disqo.springbootquickstart.service.implementation;

import com.disqo.springbootquickstart.entity.Example;
import com.disqo.springbootquickstart.exception.InvalidFormatException;
import com.disqo.springbootquickstart.repository.ExampleRepository;
import com.disqo.springbootquickstart.service.ExampleService;
import com.disqo.springbootquickstart.constant.Constants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleServiceImpl implements ExampleService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExampleService.class);
    private ExampleRepository exampleRepository;

    @Autowired
    public ExampleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public Example getExampleData(Long param1, String param2) {
        return exampleRepository.getExampleData(param1, param2);
    }


    @Override
    public Long castParam1ToValidLong(String rawParam1) {

        try {
            final Long param1 = Long.valueOf(rawParam1);
            if (param1 > 0) {
                return param1;
            }
        } catch (java.lang.NumberFormatException e) {
            logger.info(Constants.LOGGER_MESSAGE_PARAM1_INVALID_FORMAT, rawParam1);
        }
        throw new InvalidFormatException(Constants.ERROR_MESSAGE_RECORD_NOT_FOUND + rawParam1);

    }

}
