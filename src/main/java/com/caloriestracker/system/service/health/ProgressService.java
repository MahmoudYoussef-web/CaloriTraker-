package com.caloriestracker.system.service.health;

import com.caloriestracker.system.dto.request.deficit.CalorieDeficitRequest;
import com.caloriestracker.system.dto.response.dashboard.CaloriesProgressResponse;

import java.util.List;

public interface ProgressService {

    List<CaloriesProgressResponse> getCalories(Long userId);

    void setDeficit(Long userId, CalorieDeficitRequest request);
}