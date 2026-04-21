package com.example.mathapi.service;

import com.example.mathapi.exception.DivisionByZeroException;
import com.example.mathapi.exception.NegativeSqrtException;
import com.example.mathapi.model.MathResult;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    public MathResult add(double a, double b) {
        return new MathResult("ADD", a, b, a + b);
    }

    public MathResult subtract(double a, double b) {
        return new MathResult("SUBTRACT", a, b, a - b);
    }

    public MathResult multiply(double a, double b) {
        return new MathResult("MULTIPLY", a, b, a * b);
    }

    public MathResult divide(double a, double b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        return new MathResult("DIVIDE", a, b, a / b);
    }

    public MathResult sqrt(double a) {
        if (a < 0) {
            throw new NegativeSqrtException(a);
        }
        return new MathResult("SQRT", a, Math.sqrt(a));
    }

    public MathResult power(double base, double exponent) {
        return new MathResult("POWER", base, exponent, Math.pow(base, exponent));
    }

    public MathResult modulo(double a, double b) {
        if (b == 0) {
            throw new DivisionByZeroException("Modulo by zero is not allowed.");
        }
        return new MathResult("MODULO", a, b, a % b);
    }
}
