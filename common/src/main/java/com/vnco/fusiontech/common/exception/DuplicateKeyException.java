package com.vnco.fusiontech.common.exception;

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException() {
    }
    
    public DuplicateKeyException(String message) {
        super(message);
    }
}
