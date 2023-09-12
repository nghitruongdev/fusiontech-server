package com.vnco.fusiontech.common.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("Bạn không đủ quyền để truy cập ứng dụng.");
    }
    
}
