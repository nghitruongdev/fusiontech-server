package com.vnco.fusiontech.common.exception;

import jakarta.persistence.PersistenceException;

public class ViolateForeignKeyConstrainException extends PersistenceException {
    public ViolateForeignKeyConstrainException() {
        super("Cannot process the request due to foreign key constraint on the record.");
    }
}
