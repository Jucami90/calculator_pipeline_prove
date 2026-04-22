package com.example.mathapi.model;

public record MathResult(String operation, double a, double b, double result) {

    // Constructor for unary operations (e.g. sqrt)
    public MathResult(String operation, double a, double result) {
        this(operation, a, Double.NaN, result);
    }
}
