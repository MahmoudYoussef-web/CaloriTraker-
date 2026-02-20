package com.caloriestracker.system.dto.response.health;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgressPointResponse {

    private LocalDateTime date;

    private Double value;
}