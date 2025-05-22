package com.hendralw.myproject.exception;

public class DuplicateTrackingNumberException extends RuntimeException {
    public DuplicateTrackingNumberException(String message) {
        super(message);
    }
}
