package com.caloriestracker.system.service.health;

import com.caloriestracker.system.dto.request.deficit.CalorieDeficitRequest;
import com.caloriestracker.system.dto.response.dashboard.CaloriesProgressResponse;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.repository.MealRepository;

import com.caloriestracker.system.service.deficit.DeficitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final MealRepository mealRepository;
    private final DeficitService deficitService;

    @Override
    public List<CaloriesProgressResponse> getCalories(Long userId) {

        return mealRepository.findCaloriesProgress(
                userId,
                LocalDate.now().minusDays(6),
                LocalDate.now()
        );
    }

    @Override
    @Transactional
    public void setDeficit(Long userId, CalorieDeficitRequest request) {

        deficitService.setDeficit(userId, request);
    }
}