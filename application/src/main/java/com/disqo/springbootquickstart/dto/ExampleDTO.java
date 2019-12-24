package com.disqo.springbootquickstart.dto;

import java.util.Objects;

public class ExampleDTO {
    private Long param1;
    private String param2;
    private String bothParams;

    public Long getParam1() {
        return param1;
    }

    public void setParam1(Long param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getBothParams() {
        return bothParams;
    }

    public void setBothParams(String bothParams) {
        this.bothParams = bothParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleDTO exampleDTO = (ExampleDTO) o;
        return param1.equals(exampleDTO.param1) &&
          param2.equals(exampleDTO.param2) &&
          bothParams.equals(exampleDTO.bothParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(param1, param2, bothParams);
    }
}
