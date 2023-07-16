package com.vnco.fusiontech.common.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super("No record was found with the given id.");
    }
    
    public RecordNotFoundException(String message) {
        super(message);
    }
}
