package com.caloriestracker.system.controller.deficit;

import com.caloriestracker.system.dto.request.deficit.CalorieDeficitRequest;
import com.caloriestracker.system.dto.response.dashboard.CaloriesProgressResponse;
import com.caloriestracker.system.service.deficit.DeficitService;
import com.caloriestracker.system.service.health.ProgressService;
import com.caloriestracker.system.util.AuthUtils;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deficit")
@RequiredArgsConstructor
public class DeficitController {

    private final DeficitService deficitService;
    private final AuthUtils authUtils;

    @PostMapping
    public ResponseEntity<Void> setDeficit(
            @RequestBody CalorieDeficitRequest request,
            Authentication auth
    ) {

        Long userId = authUtils.getUserId(auth);

        deficitService.setDeficit(userId, request);

        return ResponseEntity.ok().build();
    }
}