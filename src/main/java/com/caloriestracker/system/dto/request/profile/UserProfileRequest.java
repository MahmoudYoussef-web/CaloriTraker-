package com.caloriestracker.system.dto.request.profile;

import com.caloriestracker.system.enums.ActivityLevel;
import com.caloriestracker.system.enums.Gender;
import com.caloriestracker.system.enums.Goal;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserProfileRequest {

    @NotNull
    private Gender gender;

    @Min(1)
    @Max(120)
    private Integer age;

    @DecimalMin("50.0")
    @DecimalMax("250.0")
    private Double heightCm;

    @DecimalMin("20.0")
    @DecimalMax("300.0")
    private Double weightKg;

    @NotNull
    private Goal goal;

    @NotNull
    private ActivityLevel activityLevel;
}
