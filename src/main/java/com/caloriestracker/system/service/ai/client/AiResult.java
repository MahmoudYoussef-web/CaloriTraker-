package com.caloriestracker.system.service.ai.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiResult {

    private String name;

    private Double calories;

    private Double quantity;

    private Double confidence;
}