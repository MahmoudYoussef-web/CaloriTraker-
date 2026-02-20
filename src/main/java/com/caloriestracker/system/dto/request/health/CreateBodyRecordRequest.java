package com.caloriestracker.system.dto.request.health;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class CreateBodyRecordRequest {

    @DecimalMin("20.0")
    @DecimalMax("300.0")
    private Double weightKg;

    @DecimalMin("50.0")
    @DecimalMax("250.0")
    private Double heightCm;
}
