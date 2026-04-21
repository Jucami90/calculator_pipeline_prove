package com.example.mathapi.exception;

public class DivisionByZeroException extends RuntimeException {

    public DivisionByZeroException() {
        super("Division by zero is not allowed.");
    }

    public DivisionByZeroException(String message) {
        super(message);
    }
}
