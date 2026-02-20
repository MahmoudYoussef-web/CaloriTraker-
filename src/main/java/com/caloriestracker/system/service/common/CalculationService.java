package com.caloriestracker.system.service.common;

import com.caloriestracker.system.enums.ActivityLevel;
import com.caloriestracker.system.enums.Gender;
import com.caloriestracker.system.enums.Goal;

public interface CalculationService {

    double calculateBmr(
            Gender gender,
            int age,
            double heightCm,
            double weightKg
    );

    double calculateTdee(double bmr, ActivityLevel level);

    double calculateTargetCalories(double tdee, Goal goal);
}