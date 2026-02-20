package com.caloriestracker.system.mapper;

import com.caloriestracker.system.dto.response.food.FoodResponse;
import com.caloriestracker.system.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodResponse toResponse(Food food) {

        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .calories(food.getCalories())
                .protein(food.getProtein())
                .carbs(food.getCarbs())
                .fat(food.getFat())
                .imageUrl(food.getImageUrl())
                .build();
    }
}