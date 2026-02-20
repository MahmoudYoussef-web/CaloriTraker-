package com.caloriestracker.system.service.ai;

import com.caloriestracker.system.dto.response.ai.AiAnalyzeResponse;
import com.caloriestracker.system.entity.*;
import com.caloriestracker.system.enums.ImageStatus;
import com.caloriestracker.system.exception.ResourceNotFoundException;
import com.caloriestracker.system.repository.*;
import com.caloriestracker.system.service.ai.client.AiClient;
import com.caloriestracker.system.service.ai.client.AiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class AiServiceImpl implements AiService {

    private final MealRepository mealRepo;
    private final MealItemRepository itemRepo;
    private final ImageRepository imageRepo;
    private final DailySummaryRepository summaryRepo;
    private final FoodRepository foodRepo;
    private final AiClient aiClient;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public AiAnalyzeResponse analyze(Long mealId, MultipartFile file) {

        Meal meal = mealRepo.findById(mealId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found")
                );

        String fileName =
                System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path = Paths.get(UPLOAD_DIR + fileName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed");
        }

        Image image = Image.builder()
                .path(path.toString())
                .status(ImageStatus.PROCESSING)
                .user(meal.getUser())
                .build();

        imageRepo.save(image);

        // Call AI
        AiResult result = aiClient.analyze(file);

        Food food = foodRepo.findByNameIgnoreCase(result.getName())
                .orElseGet(() ->
                        foodRepo.save(
                                Food.builder()
                                        .name(result.getName())
                                        .calories(result.getCalories())
                                        .protein(0.0)
                                        .carbs(0.0)
                                        .fat(0.0)
                                        .build()
                        )
                );

        MealItem item = MealItem.builder()
                .meal(meal)
                .food(food)
                .quantity(result.getQuantity())
                .caloriesAtTime(result.getCalories())
                .confidence(result.getConfidence())
                .image(image)
                .build();

        itemRepo.save(item);

        updateSummary(meal);

        image.setMealItem(item);
        image.setStatus(ImageStatus.DONE);
        imageRepo.save(image);

        AiAnalyzeResponse response = new AiAnalyzeResponse();

        response.setMealItemId(item.getId());
        response.setFoodName(food.getName());
        response.setCalories(item.getCaloriesAtTime());
        response.setQuantity(item.getQuantity());
        response.setConfidence(item.getConfidence());
        response.setImageId(image.getId());
        response.setStatus(image.getStatus());

        return response;
    }
    @Override
    @Transactional(readOnly = true)
    public ImageStatus getStatus(Long imageId) {

        Image image = imageRepo.findById(imageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Image not found")
                );

        return image.getStatus();
    }

    private void updateSummary(Meal meal) {

        LocalDate date = meal.getMealDate();
        Long userId = meal.getUser().getId();

        double consumed = mealRepo
                .findByUser_IdAndMealDate(userId, date)
                .stream().flatMap(m ->
                        m.getItems() == null
                                ? Stream.empty()
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