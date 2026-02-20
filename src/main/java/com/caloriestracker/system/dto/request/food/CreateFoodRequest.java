package com.caloriestracker.system.dto.request.food;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFoodRequest {

    @NotBlank
    private String name;

    @DecimalMin("0.0")
    private Double calories;

    @DecimalMin("0.0")
    private Double protein;

    @DecimalMin("0.0")
    private Double carbs;

    @DecimalMin("0.0")
    private Double fat;
}
