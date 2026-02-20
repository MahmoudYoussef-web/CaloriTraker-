package com.caloriestracker.system.service.meal;

import com.caloriestracker.system.dto.request.meal.AddMealItemRequest;
import com.caloriestracker.system.dto.request.meal.CreateMealRequest;
import com.caloriestracker.system.dto.response.meal.MealItemResponse;
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

        if (request.getQuantity() <= 0) {
            throw new BadRequestException("Invalid quantity");
        }

        Meal meal = mealRepo.findById(mealId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found")
                );

        // Security check
        if (!meal.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied");
        }

        Food food = foodRepo.findById(request.getFoodId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found")
                );

        double calories =
                food.getCalories() * request.getQuantity();

        MealItem item = MealItem.builder()
                .meal(meal)
                .food(food)
                .quantity(request.getQuantity())
                .caloriesAtTime(calories)
                .proteinAtTime(food.getProtein())
                .carbsAtTime(food.getCarbs())
                .fatAtTime(food.getFat())
                .build();

        itemRepo.save(item);

        updateSummary(meal);

        return mealMapper.toResponse(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public MealResponse getMeal(Long userId, Long mealId) {

        Meal meal = mealRepo.findById(mealId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found")
                );

        if (!meal.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied");
        }

        return mealMapper.toResponse(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getDailyCalories(Long userId, LocalDate date) {

        List<Meal> meals =
                mealRepo.findByUser_IdAndMealDate(userId, date);

        return meals.stream()
                .flatMap(m ->
                        m.getItems() == null
                                ? java.util.stream.Stream.empty()
                                : m.getItems().stream()
                )
                .mapToDouble(i ->
                        i.getCaloriesAtTime() == null
                                ? 0.0
                                : i.getCaloriesAtTime()
                )
                .sum();
    }
    private void updateSummary(Meal meal) {

        LocalDate date = meal.getMealDate();
        Long userId = meal.getUser().getId();

        double consumed = mealRepo
                .findByUser_IdAndMealDate(userId, date)
                .stream()
                .flatMap(m ->
                        m.getItems() == null
                                ? java.util.stream.Stream.empty()
                                : m.getItems().stream()
                )
                .mapToDouble(i ->
                        i.getCaloriesAtTime() == null
                                ? 0.0
                                : i.getCaloriesAtTime()
                )
                .sum();

        DailySummary summary = summaryRepo
                .findByUserIdAndDate(userId, date)
                .orElse(new DailySummary());

        summary.setUser(meal.getUser());
        summary.setDate(date);
        summary.setConsumedCalories(consumed);

        summaryRepo.save(summary);
    }
}
