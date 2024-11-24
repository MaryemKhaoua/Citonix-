package com.example.citronix.exception;

public class InsufficientRecolteQuantityException extends RuntimeException {
    public InsufficientRecolteQuantityException(String message) {
        super(message);
    }
}
