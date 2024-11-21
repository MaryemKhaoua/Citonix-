package com.example.citronix.exception;

public class ChampNotFoundException extends RuntimeException {
    public ChampNotFoundException(String message) {
        super(message);
    }
}
