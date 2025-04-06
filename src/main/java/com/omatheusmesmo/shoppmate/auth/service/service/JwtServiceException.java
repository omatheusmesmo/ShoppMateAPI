package com.omatheusmesmo.shoppmate.auth.service.service;

public class JwtServiceException extends RuntimeException {
    public JwtServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
