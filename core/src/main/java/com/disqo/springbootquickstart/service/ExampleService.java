package com.disqo.springbootquickstart.service;

import com.disqo.springbootquickstart.entity.Example;

public interface ExampleService {
    Example getExampleData(Long param1, String param2);
    Long castParam1ToValidLong(String rawParam1);
}
