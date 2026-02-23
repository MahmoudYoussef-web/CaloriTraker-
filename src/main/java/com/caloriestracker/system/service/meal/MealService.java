package com.caloriestracker.system.service.meal;

import com.caloriestracker.system.dto.request.meal.AddMealItemRequest;
import com.caloriestracker.system.dto.request.meal.CreateMealRequest;
import com.caloriestracker.system.dto.request.meal.ManualMealItemRequest;
import com.caloriestracker.system.dto.response.meal.MealResponse;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    MealResponse createMeal(Long userId, CreateMealRequest request);

    MealResponse addItem(Long userId, Long mealId, AddMealItemRequest request);

    MealResponse addManualItem(Long userId, Long mealId, ManualMealItemRequest request);

    MealResponse updateItem(Long userId, Long itemId, AddMealItemRequest request);

    void deleteItem(Long userId, Long itemId);

    MealResponse getMeal(Long userId, Long mealId);

    List<MealResponse> getMealsByDate(Long userId, LocalDate date);

    Double getDailyCalories(Long userId, LocalDate date);
}