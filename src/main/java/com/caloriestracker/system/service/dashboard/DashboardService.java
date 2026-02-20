package com.caloriestracker.system.service.dashboard;

import com.caloriestracker.system.dto.response.dashboard.CaloriesProgressResponse;
import com.caloriestracker.system.dto.response.dashboard.DailyDashboardResponse;

import java.time.LocalDate;
import java.util.List;

public interface DashboardService {

    DailyDashboardResponse getDaily(Long userId, LocalDate date);

    List<CaloriesProgressResponse> getWeeklyCalories(Long userId);
}