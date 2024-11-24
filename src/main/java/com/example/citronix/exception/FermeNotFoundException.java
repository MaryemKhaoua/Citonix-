package com.example.citronix.exception;


public class FermeNotFoundException extends RuntimeException {
    public FermeNotFoundException(String message) {
        super(message);
    }
}