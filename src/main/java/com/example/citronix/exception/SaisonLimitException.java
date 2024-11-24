package com.example.citronix.exception;

public class SaisonLimitException extends RuntimeException {
    public SaisonLimitException(String message) {
        super(message);
    }
}
