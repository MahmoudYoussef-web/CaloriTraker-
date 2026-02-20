package com.caloriestracker.system.dto.response.health;

import lombok.Data;

@Data
public class MacrosResponse {

    private Double calories;

    private Double protein;

    private Double carbs;

    private Double fat;
}