package com.example.citronix.exception;

public class ExceededTreeDensityException extends RuntimeException {
  public ExceededTreeDensityException() {
    super("Field exceeds the maximum allowed tree density.");
  }
}
