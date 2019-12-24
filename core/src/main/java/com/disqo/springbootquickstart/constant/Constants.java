package com.disqo.springbootquickstart.constant;

public final class Constants {

    public static final String PARAM_1_KEY = "param1";
    public static final String PARAM_2_KEY = "param2";

    public static final String ERROR_CODE_INVALID_FIELD_VALUE = "001";
    public static final String ERROR_CODE_RECORD_NOT_FOUND = "002";

    public static final String ERROR_MESSAGE_RECORD_NOT_FOUND = "Could not find record with specified fields: (%s, %s)";
    public static final String ERROR_MESSAGE_ENDPOINT_NOT_FOUND = "Not a valid endpoint";

    public static final String LOGGER_MESSAGE_PARAM1_INVALID_FORMAT = "Param1 '{}' failed numeric format validation";

    private Constants() {
        throw new AssertionError();
    }

}
