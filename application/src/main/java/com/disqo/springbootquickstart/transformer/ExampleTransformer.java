package com.disqo.springbootquickstart.transformer;

import com.disqo.springbootquickstart.dto.ExampleDTO;
import com.disqo.springbootquickstart.entity.Example;

import org.springframework.stereotype.Component;

@Component
public class ExampleTransformer {
    public ExampleDTO transform(Example example) {

        final ExampleDTO exampleDto = new ExampleDTO();
        exampleDto.setParam1(example.getParam1());
        exampleDto.setParam2(example.getParam2());

        if (example.getParam1() != null && example.getParam2() != null) {
            exampleDto.setBothParams(String.join("-", example.getParam1().toString(), example.getParam2()));
        }

        return exampleDto;
    }

}
