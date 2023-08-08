package com.vnco.fusiontech.common.exception;

import jakarta.persistence.PersistenceException;

public class ViolateForeignKeyConstraintException extends PersistenceException {
    public ViolateForeignKeyConstraintException() {
        super("Cannot process the request due to foreign key constraint on the record.");
    }
}
