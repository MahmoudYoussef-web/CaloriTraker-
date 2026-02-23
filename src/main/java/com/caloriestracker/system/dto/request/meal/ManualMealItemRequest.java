package com.caloriestracker.system.dto.request.meal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ManualMealItemRequest {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0.0")
    private Double calories;

    @NotNull
    @DecimalMin("0.0")
    private Double protein;

    @NotNull
    @DecimalMin("0.0")
    private Double carbs;

    @NotNull
    @DecimalMin("0.0")
    private Double fat;

    @NotNull
    @DecimalMin("0.1")
    private Double quantity;
}