package com.caloriestracker.system.controller;

import com.caloriestracker.system.dto.request.meal.AddMealItemRequest;
import com.caloriestracker.system.dto.request.meal.CreateMealRequest;
import com.caloriestracker.system.dto.request.meal.ManualMealItemRequest;
import com.caloriestracker.system.dto.response.meal.MealResponse;
import com.caloriestracker.system.service.meal.MealService;
import com.caloriestracker.system.util.AuthUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final AuthUtils authUtils;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(
            @Valid @RequestBody CreateMealRequest request,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mealService.createMeal(userId, request));
    }

    @PostMapping("/{mealId}/items")
    public ResponseEntity<MealResponse> addItem(
            @PathVariable Long mealId,
            @Valid @RequestBody AddMealItemRequest request,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.ok(
                mealService.addItem(userId, mealId, request)
        );
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<MealResponse> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody AddMealItemRequest request,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.ok(
                mealService.updateItem(userId, itemId, request)
        );
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        mealService.deleteItem(userId, itemId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{mealId}")
    public ResponseEntity<MealResponse> getMeal(
            @PathVariable Long mealId,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.ok(
                mealService.getMeal(userId, mealId)
        );
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<MealResponse>> getMealsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.ok(
                mealService.getMealsByDate(userId, date)
        );
    }

    @GetMapping("/daily-calories")
    public ResponseEntity<Double> getDailyCalories(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.ok(
                mealService.getDailyCalories(
                        userId,
                        date != null ? date : LocalDate.now()
                )
        );
    }
    @PostMapping("/{mealId}/items/manual")
    public ResponseEntity<MealResponse> addManualItem(
            @PathVariable Long mealId,
            @Valid @RequestBody ManualMealItemRequest request,
            Authentication authentication
    ) {

        Long userId = authUtils.getUserId(authentication);

        return ResponseEntity.ok(
                mealService.addManualItem(userId, mealId, request)
        );
    }
}