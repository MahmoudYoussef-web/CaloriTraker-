package com.caloriestracker.system.controller.dashboard;

import com.caloriestracker.system.dto.response.dashboard.CaloriesProgressResponse;
import com.caloriestracker.system.dto.response.dashboard.DailyDashboardResponse;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.service.dashboard.DashboardService;
import com.caloriestracker.system.util.AuthUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final AuthUtils authUtils;

    @GetMapping("/daily")
    public ResponseEntity<DailyDashboardResponse> getDaily(
            Authentication auth,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {

        if (date != null && date.isAfter(LocalDate.now())) {
            throw new BadRequestException("Date cannot be in the future");
        }

        Long userId = authUtils.getUserId(auth);

        LocalDate targetDate =
                (date != null) ? date : LocalDate.now();

        return ResponseEntity.ok(
                dashboardService.getDaily(userId, targetDate)
        );
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<CaloriesProgressResponse>> getWeekly(
            Authentication auth
    ) {

        Long userId = authUtils.getUserId(auth);

        return ResponseEntity.ok(
                dashboardService.getWeeklyCalories(userId)
        );
    }
}