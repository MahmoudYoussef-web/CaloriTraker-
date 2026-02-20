package com.caloriestracker.system.service.food;

import com.caloriestracker.system.dto.response.food.FoodResponse;

import java.util.List;

public interface FoodService {

    List<FoodResponse> getAllFoods();

    FoodResponse getById(Long id);

    FoodResponse getByName(String name);

}