package com.example.mathapi.model;

import jakarta.validation.constraints.NotNull;

public record MathRequest(
        @NotNull(message = "First operand must not be null") Double a,
        @NotNull(message = "Second operand must not be null") Double b
) {}
