package com.vnco.fusiontech.common.exception;

public class RecordExistsException extends RuntimeException {
    public RecordExistsException() {
        super("Record already exists with the given id.");
    }
    
    public RecordExistsException(String message) {
        super(message);
    }
}
