package com.caloriestracker.system.dto.response.ai;

import com.caloriestracker.system.enums.ImageStatus;
import lombok.Data;

@Data
public class AiAnalyzeResponse {

    private Long mealItemId;

    private String foodName;

    private Double calories;
    private Double quantity;

    private Double confidence;

    private Long imageId;

    private ImageStatus status;
}