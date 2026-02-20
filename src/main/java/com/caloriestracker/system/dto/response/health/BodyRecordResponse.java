package com.caloriestracker.system.dto.response.health;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BodyRecordResponse {

    private Long id;

    private Double weightKg;

    private Double heightCm;

    private Double bmi;

    private LocalDateTime recordedAt;
}