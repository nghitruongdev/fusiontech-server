package com.vnco.fusiontech.common.exception;

public class RecordExistsException extends jakarta.persistence.EntityExistsException {
    public RecordExistsException() {
        super("Record already exists with the given id.");
    }
}
