package com.caloriestracker.system.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyDashboardResponse {

    private LocalDate date;

    private Double consumedCalories;

    private Double targetCalories;

    private Double remainingCalories;

    private String status;
}