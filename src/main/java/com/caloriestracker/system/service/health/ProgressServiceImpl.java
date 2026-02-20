package com.caloriestracker.system.service.health;

import com.caloriestracker.system.dto.request.deficit.CalorieDeficitRequest;
import com.caloriestracker.system.dto.response.dashboard.CaloriesProgressResponse;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.repository.MealRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final MealRepository mealRepository;

    @Override
    public List<CaloriesProgressResponse> getCalories(Long userId) {

        return mealRepository.findCaloriesProgress(
                userId,
                java.time.LocalDate.now().minusDays(6),
                java.time.LocalDate.now()
        );
    }

    @Override
    @Transactional
    public void setDeficit(Long userId, CalorieDeficitRequest request) {

        if (request.getMaintenanceCalories() <= 0) {
            throw new BadRequestException("Maintenance calories must be positive");
        }

        if (request.getDeficit() < 0) {
            throw new BadRequestException("Deficit cannot be negative");
        }

        if (request.getDeficit() > request.getMaintenanceCalories()) {
            throw new BadRequestException("Deficit cannot exceed maintenance calories");
        }

    }
}