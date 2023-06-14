package com.vnco.fusiontech.common.exception;

public class RecordNotFoundException extends jakarta.persistence.EntityNotFoundException {
    public RecordNotFoundException() {
        super("No record was found with the given id.");
    }
    
    public RecordNotFoundException(String message) {
        super(message);
    }
}
