package com.example.mathapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Math API Integration Tests")
class MathApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String body(double a, double b) throws Exception {
        return objectMapper.writeValueAsString(Map.of("a", a, "b", b));
    }

    // ─────────────────────────────────────────────
    //  ADD
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("ADD integration")
    class AddIntegration {

        @Test
        @DisplayName("3 + 5 = 8")
        void addReturnsCorrectResult() throws Exception {
            mockMvc.perform(post("/api/math/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(3, 5)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.operation").value("ADD"))
                    .andExpect(jsonPath("$.a").value(3.0))
                    .andExpect(jsonPath("$.b").value(5.0))
                    .andExpect(jsonPath("$.result").value(8.0));
        }

        @Test
        @DisplayName("null field returns 422")
        void addNullFieldReturns422() throws Exception {
            mockMvc.perform(post("/api/math/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"a\": null, \"b\": 5}"))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.status").value(422));
        }
    }

    // ─────────────────────────────────────────────
    //  SUBTRACT
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("SUBTRACT integration")
    class SubtractIntegration {

        @Test
        @DisplayName("10 - 4 = 6")
        void subtractReturnsCorrectResult() throws Exception {
            mockMvc.perform(post("/api/math/subtract")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(10, 4)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(6.0));
        }
    }

    // ─────────────────────────────────────────────
    //  MULTIPLY
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("MULTIPLY integration")
    class MultiplyIntegration {

        @Test
        @DisplayName("6 * 7 = 42")
        void multiplyReturnsCorrectResult() throws Exception {
            mockMvc.perform(post("/api/math/multiply")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(6, 7)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(42.0));
        }
    }

    // ─────────────────────────────────────────────
    //  DIVIDE
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("DIVIDE integration")
    class DivideIntegration {

        @Test
        @DisplayName("15 / 3 = 5")
        void divideSuccess() throws Exception {
            mockMvc.perform(post("/api/math/divide")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(15, 3)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(5.0));
        }

        @Test
        @DisplayName("division by zero returns 400")
        void divideByZeroReturns400() throws Exception {
            mockMvc.perform(post("/api/math/divide")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(10, 0)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.message").value("Division by zero is not allowed."));
        }
    }

    // ─────────────────────────────────────────────
    //  SQRT
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("SQRT integration")
    class SqrtIntegration {

        @Test
        @DisplayName("sqrt(144) = 12")
        void sqrtSuccess() throws Exception {
            mockMvc.perform(get("/api/math/sqrt").param("value", "144"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(12.0));
        }

        @Test
        @DisplayName("sqrt of negative returns 400")
        void sqrtNegativeReturns400() throws Exception {
            mockMvc.perform(get("/api/math/sqrt").param("value", "-1"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400));
        }
    }

    // ─────────────────────────────────────────────
    //  POWER
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("POWER integration")
    class PowerIntegration {

        @Test
        @DisplayName("2^10 = 1024")
        void powerSuccess() throws Exception {
            mockMvc.perform(post("/api/math/power")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(2, 10)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(1024.0));
        }
    }

    // ─────────────────────────────────────────────
    //  MODULO
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("MODULO integration")
    class ModuloIntegration {

        @Test
        @DisplayName("10 % 3 = 1")
        void moduloSuccess() throws Exception {
            mockMvc.perform(post("/api/math/modulo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(10, 3)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(1.0));
        }

        @Test
        @DisplayName("modulo by zero returns 400")
        void moduloByZeroReturns400() throws Exception {
            mockMvc.perform(post("/api/math/modulo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body(10, 0)))
                    .andExpect(status().isBadRequest());
        }
    }
}
