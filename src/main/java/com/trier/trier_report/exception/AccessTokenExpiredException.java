package com.trier.trier_report.exception;

public class AccessTokenExpiredException extends  RuntimeException{
    public AccessTokenExpiredException(String message) {
        super(message);
    }
}
