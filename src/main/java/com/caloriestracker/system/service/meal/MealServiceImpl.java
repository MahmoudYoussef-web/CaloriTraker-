package com.caloriestracker.system.service.meal;

import com.caloriestracker.system.dto.request.meal.AddMealItemRequest;
import com.caloriestracker.system.dto.request.meal.CreateMealRequest;
import com.caloriestracker.system.dto.request.meal.ManualMealItemRequest;
import com.caloriestracker.system.dto.response.meal.MealResponse;
import com.caloriestracker.system.entity.*;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.exception.ResourceNotFoundException;
import com.caloriestracker.system.mapper.MealMapper;
import com.caloriestracker.system.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepo;
    private final MealItemRepository itemRepo;
    private final FoodRepository foodRepo;
    private final UserRepository userRepo;
    private final MealMapper mealMapper;
    private final DailySummaryRepository summaryRepo;

    @Override
    @Transactional
    public MealResponse createMeal(Long userId, CreateMealRequest request) {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        mealRepo.findByUser_IdAndMealDateAndMealType(
                userId,
                request.getDate(),
                request.getMealType()
        ).ifPresent(m -> {
            throw new BadRequestException("Meal already exists");
        });

        Meal meal = Meal.builder()
                .mealDate(request.getDate())
                .mealType(request.getMealType())
                .user(user)
                .build();

        mealRepo.save(meal);

        return mealMapper.toResponse(meal);
    }

    @Override
    @Transactional
    public MealResponse addItem(
            Long userId,
            Long mealId,
            AddMealItemRequest request
    ) {

        validateQuantity(request.getQuantity());

        Meal meal = mealRepo.findById(mealId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found")
                );

        checkAccess(meal, userId);

        Food food = foodRepo.findById(request.getFoodId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found")
                );

        MealItem item = buildItem(meal, food, request.getQuantity());

        itemRepo.save(item);

        updateSummary(meal);

        return mealMapper.toResponse(meal);
    }

    @Override
    @Transactional
    public MealResponse updateItem(
            Long userId,
            Long itemId,
            AddMealItemRequest request
    ) {

        validateQuantity(request.getQuantity());

        MealItem item = itemRepo
                .findByIdAndMeal_User_Id(itemId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found")
                );

        Food food = foodRepo.findById(request.getFoodId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found")
                );

        double calories =
                food.getCalories() * request.getQuantity();

        item.setFood(food);
        item.setQuantity(request.getQuantity());
        item.setCaloriesAtTime(calories);
        item.setProteinAtTime(food.getProtein());
        item.setCarbsAtTime(food.getCarbs());
        item.setFatAtTime(food.getFat());

        itemRepo.save(item);

        updateSummary(item.getMeal());

        return mealMapper.toResponse(item.getMeal());
    }

    @Override
    @Transactional
    public void deleteItem(Long userId, Long itemId) {

        MealItem item = itemRepo
                .findByIdAndMeal_User_Id(itemId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found")
                );

        Meal meal = item.getMeal();

        itemRepo.delete(item);

        updateSummary(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public MealResponse getMeal(Long userId, Long mealId) {

        Meal meal = mealRepo.findById(mealId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found")
                );

        checkAccess(meal, userId);

        return mealMapper.toResponse(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealResponse> getMealsByDate(
            Long userId,
            LocalDate date
    ) {

        List<Meal> meals =
                mealRepo.findByUser_IdAndMealDate(userId, date);

        return meals.stream()
                .map(mealMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Double getDailyCalories(Long userId, LocalDate date) {

        List<Meal> meals =
                mealRepo.findByUser_IdAndMealDate(userId, date);

        return meals.stream()
                .flatMap(m -> m.getItems().stream())
                .mapToDouble(i ->
                        i.getCaloriesAtTime() == null
                                ? 0.0
                                : i.getCaloriesAtTime()
                )
                .sum();
    }

    /* ================= Helpers ================= */

    private void validateQuantity(Double q) {

        if (q == null || q <= 0) {
            throw new BadRequestException("Invalid quantity");
        }
    }

    private void checkAccess(Meal meal, Long userId) {

        if (!meal.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied");
        }
    }

    private MealItem buildItem(
            Meal meal,
            Food food,
            Double quantity
    ) {

        double calories = food.getCalories() * quantity;

        return MealItem.builder()
                .meal(meal)
                .food(food)
                .quantity(quantity)
                .caloriesAtTime(calories)
                .proteinAtTime(food.getProtein())
                .carbsAtTime(food.getCarbs())
                .fatAtTime(food.getFat())
                .build();
    }

    private void updateSummary(Meal meal) {

        LocalDate date = meal.getMealDate();
        Long userId = meal.getUser().getId();

        double consumed = mealRepo
                .findByUser_IdAndMealDate(userId, date)
                .stream()
                .flatMap(m -> m.getItems().stream())
                .mapToDouble(i ->
                        i.getCaloriesAtTime() == null
                                ? 0.0
                                : i.getCaloriesAtTime()
                )
                .sum();

        DailySummary summary = summaryRepo
                .findByUserIdAndDate(userId, date)
                .orElse(
                        DailySummary.builder()
                                .user(meal.getUser())
                                .date(date)
                                .build()
                );

        summary.setConsumedCalories(consumed);

        if (summary.getTargetCalories() == null) {
            summary.setTargetCalories(0.0);
        }

        summaryRepo.save(summary);
    }
    @Override
    @Transactional
    public MealResponse addManualItem(
            Long userId,
            Long mealId,
            ManualMealItemRequest request
    ) {

        validateQuantity(request.getQuantity());

        Meal meal = mealRepo.findById(mealId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found")
                );

        checkAccess(meal, userId);

        Food food = foodRepo.findByNameIgnoreCase(request.getName())
                .orElseGet(() -> {

                    Food newFood = Food.builder()
                            .name(request.getName())
                            .calories(request.getCalories())
                            .protein(request.getProtein())
                            .carbs(request.getCarbs())
                            .fat(request.getFat())
                            .build();

                    return foodRepo.save(newFood);
                });

        double calories =
                request.getCalories() * request.getQuantity();

        MealItem item = MealItem.builder()
                .meal(meal)
                .food(food)
                .quantity(request.getQuantity())
                .caloriesAtTime(calories)
                .proteinAtTime(request.getProtein())
                .carbsAtTime(request.getCarbs())
                .fatAtTime(request.getFat())
                .confidence(1.0)
                .build();

        itemRepo.save(item);

        updateSummary(meal);

        return mealMapper.toResponse(meal);
    }
}