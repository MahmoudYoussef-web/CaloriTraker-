package com.caloriestracker.system.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CaloriesProgressResponse {

    private LocalDate date;

    private Double consumed;

    private Double target;

    public CaloriesProgressResponse(LocalDate date, Double consumed) {
        this.date = date;
        this.consumed = consumed;
        this.target = 0.0;
    }

    public CaloriesProgressResponse(
            LocalDate date,
            Double consumed,
            Double target
    ) {
        this.date = date;
        this.consumed = consumed;
        this.target = target;
    }
}