package com.caloriestracker.system.dto.response.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealItemResponse {

    private Long foodId;
    private String foodName;

    private Double quantity;
    private Double calories;

    private Double confidence;

    private Long imageId;
}