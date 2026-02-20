package com.caloriestracker.system.controller.bmi;

import com.caloriestracker.system.dto.request.health.BmiRequest;
import com.caloriestracker.system.dto.response.health.BmiResponse;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.service.health.bmi.BmiService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bmi")
@RequiredArgsConstructor
public class BmiController {

    private final BmiService bmiService;

    @PostMapping("/calculate")
    public ResponseEntity<BmiResponse> calculate(
            @Valid @RequestBody BmiRequest request
    ) {

        if (request.getHeightCm() <= 0 || request.getWeightKg() <= 0) {
            throw new BadRequestException("Invalid height or weight");
        }

        return ResponseEntity.ok(
                bmiService.calculate(request)
        );
    }
}