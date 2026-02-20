package com.caloriestracker.system.service.meal;

import com.caloriestracker.system.dto.request.meal.AddMealItemRequest;
import com.caloriestracker.system.dto.request.meal.CreateMealRequest;
import com.caloriestracker.system.dto.response.meal.MealResponse;

import java.time.LocalDate;

public interface MealService {

    MealResponse createMeal(Long userId, CreateMealRequest request);

    MealResponse addItem(Long userId, Long mealId, AddMealItemRequest request);

    MealResponse getMeal(Long userId, Long mealId);

    Double getDailyCalories(Long userId, LocalDate date);
}