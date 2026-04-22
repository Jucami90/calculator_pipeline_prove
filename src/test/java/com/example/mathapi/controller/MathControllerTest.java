package com.example.mathapi.controller;

import com.example.mathapi.exception.DivisionByZeroException;
import com.example.mathapi.exception.NegativeSqrtException;
import com.example.mathapi.model.MathResult;
import com.example.mathapi.service.MathService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MathController.class)
@DisplayName("MathController Unit Tests (MockMvc)")
class MathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathService mathService;

    @Autowired
    private ObjectMapper objectMapper;

    private String json(double a, double b) throws Exception {
        return objectMapper.writeValueAsString(new java.util.HashMap<>() {{
            put("a", a);
            put("b", b);
        }});
    }

    // ─────────────────────────────────────────────
    //  ADD
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("POST /api/math/add")
    class AddEndpoint {

        @Test
        @DisplayName("should return 200 with correct result")
        void addSuccess() throws Exception {
            when(mathService.add(3, 5)).thenReturn(new MathResult("ADD", 3, 5, 8));

            mockMvc.perform(post("/api/math/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json(3, 5)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.operation").value("ADD"))
                    .andExpect(jsonPath("$.result").value(8.0));
        }

        @Test
        @DisplayName("should return 422 when body is invalid")
        void addInvalidBody() throws Exception {
            mockMvc.perform(post("/api/math/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"a\": null, \"b\": 5}"))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    // ─────────────────────────────────────────────
    //  DIVIDE
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("POST /api/math/divide")
    class DivideEndpoint {

        @Test
        @DisplayName("should return 200 with division result")
        void divideSuccess() throws Exception {
            when(mathService.divide(10, 2)).thenReturn(new MathResult("DIVIDE", 10, 2, 5));

            mockMvc.perform(post("/api/math/divide")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json(10, 2)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(5.0));
        }

        @Test
        @DisplayName("should return 400 when dividing by zero")
        void divideByZero() throws Exception {
            when(mathService.divide(5, 0)).thenThrow(new DivisionByZeroException());

            mockMvc.perform(post("/api/math/divide")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json(5, 0)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").exists());
        }
    }

    // ─────────────────────────────────────────────
    //  SQRT
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("GET /api/math/sqrt")
    class SqrtEndpoint {

        @Test
        @DisplayName("should return 200 with sqrt result")
        void sqrtSuccess() throws Exception {
            when(mathService.sqrt(16)).thenReturn(new MathResult("SQRT", 16, 4));

            mockMvc.perform(get("/api/math/sqrt").param("value", "16"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(4.0));
        }

        @Test
        @DisplayName("should return 400 for negative input")
        void sqrtNegative() throws Exception {
            when(mathService.sqrt(-4)).thenThrow(new NegativeSqrtException(-4));

            mockMvc.perform(get("/api/math/sqrt").param("value", "-4"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").exists());
        }
    }

    // ─────────────────────────────────────────────
    //  POWER
    // ─────────────────────────────────────────────
    @Nested
    @DisplayName("POST /api/math/power")
    class PowerEndpoint {

        @Test
        @DisplayName("should return 200 with power result")
        void powerSuccess() throws Exception {
            when(mathService.power(2, 8)).thenReturn(new MathResult("POWER", 2, 8, 256));

            mockMvc.perform(post("/api/math/power")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json(2, 8)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(256.0));
        }
    }
}
