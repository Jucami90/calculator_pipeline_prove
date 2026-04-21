package com.example.mathapi.controller;

import com.example.mathapi.model.MathRequest;
import com.example.mathapi.model.MathResult;
import com.example.mathapi.service.MathService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/math")
public class MathController {

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @PostMapping("/add")
    public ResponseEntity<MathResult> add(@Valid @RequestBody MathRequest request) {
        return ResponseEntity.ok(mathService.add(request.a(), request.b()));
    }

    @PostMapping("/subtract")
    public ResponseEntity<MathResult> subtract(@Valid @RequestBody MathRequest request) {
        return ResponseEntity.ok(mathService.subtract(request.a(), request.b()));
    }

    @PostMapping("/multiply")
    public ResponseEntity<MathResult> multiply(@Valid @RequestBody MathRequest request) {
        return ResponseEntity.ok(mathService.multiply(request.a(), request.b()));
    }

    @PostMapping("/divide")
    public ResponseEntity<MathResult> divide(@Valid @RequestBody MathRequest request) {
        return ResponseEntity.ok(mathService.divide(request.a(), request.b()));
    }

    @GetMapping("/sqrt")
    public ResponseEntity<MathResult> sqrt(@RequestParam double value) {
        return ResponseEntity.ok(mathService.sqrt(value));
    }

    @PostMapping("/power")
    public ResponseEntity<MathResult> power(@Valid @RequestBody MathRequest request) {
        return ResponseEntity.ok(mathService.power(request.a(), request.b()));
    }

    @PostMapping("/modulo")
    public ResponseEntity<MathResult> modulo(@Valid @RequestBody MathRequest request) {
        return ResponseEntity.ok(mathService.modulo(request.a(), request.b()));
    }
}
