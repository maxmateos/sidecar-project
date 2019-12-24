package com.disqo.springbootquickstart.repository;

import com.disqo.springbootquickstart.entity.Example;

public interface ExampleRepository {
    Example getExampleData(Long param1, String param2);
}
