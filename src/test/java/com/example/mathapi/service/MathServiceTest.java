package com.example.mathapi.service;

import com.example.mathapi.exception.DivisionByZeroException;
import com.example.mathapi.exception.NegativeSqrtException;
import com.example.mathapi.model.MathResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MathService Unit Tests")
class MathServiceTest {

    private MathService mathService;

    @BeforeEach
    void setUp() {
        mathService = new MathService();
    }

    // ─────────────────────────────────────────────
    //  ADD
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("add()")
    class AddTests {

        @Test
        @DisplayName("should add two positive numbers")
        void addPositiveNumbers() {
            MathResult result = mathService.add(3, 5);
            assertThat(result.result()).isEqualTo(8.0);
            assertThat(result.operation()).isEqualTo("ADD");
        }

        @Test
        @DisplayName("should add a positive and a negative number")
        void addWithNegative() {
            MathResult result = mathService.add(10, -4);
            assertThat(result.result()).isEqualTo(6.0);
        }

        @Test
        @DisplayName("should return zero when adding inverses")
        void addInverses() {
            MathResult result = mathService.add(7, -7);
            assertThat(result.result()).isEqualTo(0.0);
        }
    }

    // ─────────────────────────────────────────────
    //  SUBTRACT
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("subtract()")
    class SubtractTests {

        @Test
        @DisplayName("should subtract correctly")
        void subtractPositiveNumbers() {
            MathResult result = mathService.subtract(10, 3);
            assertThat(result.result()).isEqualTo(7.0);
        }

        @Test
        @DisplayName("should return negative when b > a")
        void subtractResultNegative() {
            MathResult result = mathService.subtract(3, 10);
            assertThat(result.result()).isEqualTo(-7.0);
        }
    }

    // ─────────────────────────────────────────────
    //  MULTIPLY
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("multiply()")
    class MultiplyTests {

        @Test
        @DisplayName("should multiply two numbers")
        void multiplyPositive() {
            MathResult result = mathService.multiply(4, 5);
            assertThat(result.result()).isEqualTo(20.0);
        }

        @Test
        @DisplayName("should return zero when multiplying by zero")
        void multiplyByZero() {
            MathResult result = mathService.multiply(99, 0);
            assertThat(result.result()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("should return negative when signs differ")
        void multiplyDifferentSigns() {
            MathResult result = mathService.multiply(-3, 5);
            assertThat(result.result()).isEqualTo(-15.0);
        }
    }

    // ─────────────────────────────────────────────
    //  DIVIDE
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("divide()")
    class DivideTests {

        @Test
        @DisplayName("should divide correctly")
        void dividePositiveNumbers() {
            MathResult result = mathService.divide(10, 2);
            assertThat(result.result()).isEqualTo(5.0);
        }

        @Test
        @DisplayName("should return a decimal result")
        void divideDecimalResult() {
            MathResult result = mathService.divide(7, 2);
            assertThat(result.result()).isEqualTo(3.5);
        }

        @Test
        @DisplayName("should throw DivisionByZeroException when dividing by zero")
        void divideByZeroThrows() {
            assertThatThrownBy(() -> mathService.divide(5, 0))
                    .isInstanceOf(DivisionByZeroException.class)
                    .hasMessageContaining("zero");
        }
    }

    // ─────────────────────────────────────────────
    //  SQRT
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("sqrt()")
    class SqrtTests {

        @Test
        @DisplayName("should calculate square root of a perfect square")
        void sqrtPerfectSquare() {
            MathResult result = mathService.sqrt(25);
            assertThat(result.result()).isEqualTo(5.0);
        }

        @Test
        @DisplayName("should calculate square root of zero")
        void sqrtZero() {
            MathResult result = mathService.sqrt(0);
            assertThat(result.result()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("should throw NegativeSqrtException for negative input")
        void sqrtNegativeThrows() {
            assertThatThrownBy(() -> mathService.sqrt(-9))
                    .isInstanceOf(NegativeSqrtException.class)
                    .hasMessageContaining("-9.0");
        }
    }

    // ─────────────────────────────────────────────
    //  POWER
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("power()")
    class PowerTests {

        @Test
        @DisplayName("should calculate power correctly")
        void powerPositive() {
            MathResult result = mathService.power(2, 10);
            assertThat(result.result()).isEqualTo(1024.0);
        }

        @Test
        @DisplayName("should return 1 for any base to power 0")
        void powerToZero() {
            MathResult result = mathService.power(99, 0);
            assertThat(result.result()).isEqualTo(1.0);
        }

        @Test
        @DisplayName("should handle negative exponent")
        void powerNegativeExponent() {
            MathResult result = mathService.power(2, -1);
            assertThat(result.result()).isEqualTo(0.5);
        }
    }

    // ─────────────────────────────────────────────
    //  MODULO
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("modulo()")
    class ModuloTests {

        @Test
        @DisplayName("should calculate modulo correctly")
        void moduloBasic() {
            MathResult result = mathService.modulo(10, 3);
            assertThat(result.result()).isEqualTo(1.0);
        }

        @Test
        @DisplayName("should throw DivisionByZeroException when modulo by zero")
        void moduloByZeroThrows() {
            assertThatThrownBy(() -> mathService.modulo(5, 0))
                    .isInstanceOf(DivisionByZeroException.class);
        }
    }
}
