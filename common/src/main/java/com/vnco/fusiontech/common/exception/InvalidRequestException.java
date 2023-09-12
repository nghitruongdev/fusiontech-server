package com.vnco.fusiontech.common.exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String message) {
        super(message);
    }
    
    public InvalidRequestException() {
        super("Request sent was invalid.");
    }
}
