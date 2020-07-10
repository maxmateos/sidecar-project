package com.project.sidecarhealth.enumeration;

import java.util.NoSuchElementException;

public enum ErrorCodes {

    EMPTY_REQUIRED_FIELD_CODE("001"),
    INVALID_VALUE_CODE("002"),
    RECORD_NOT_FOUND_CODE("003"),
    RECORD_ALREADY_EXISTS_CODE("004");

    private String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ErrorCodes fromText(String text) {

        for (ErrorCodes device : ErrorCodes.values()) {
            if (device.getCode().equals(text)) {
                return device;
            }
        }
        throw new NoSuchElementException("Invalid ErrorCode: " + text);
    }
}
