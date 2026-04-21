package com.example.mathapi.exception;

public class NegativeSqrtException extends RuntimeException {

    public NegativeSqrtException(double value) {
        super("Cannot compute square root of a negative number: " + value);
    }
}
