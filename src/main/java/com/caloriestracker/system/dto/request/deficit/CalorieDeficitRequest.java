package com.caloriestracker.system.dto.request.deficit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CalorieDeficitRequest {

    @Min(800)
    @Max(6000)
    private Integer maintenanceCalories;

    @Min(0)
    @Max(1500)
    private Integer deficit;
}
