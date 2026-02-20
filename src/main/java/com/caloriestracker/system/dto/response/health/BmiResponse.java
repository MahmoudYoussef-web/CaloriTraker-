package com.caloriestracker.system.dto.response.health;

import lombok.Data;

@Data
public class BmiResponse {

    private Double bmi;

    private String category;

    private Double dailyCalories;
}