package com.disqo.springbootquickstart.entity;

import java.util.Objects;

public class Example {
    private Long param1;
    private String param2;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;
        return param1.equals(example.param1) &&
                param2.equals(example.param2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(param1, param2);
    }
}
