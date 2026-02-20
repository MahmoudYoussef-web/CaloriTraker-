package com.caloriestracker.system.service.health;

import com.caloriestracker.system.dto.request.health.CreateBodyRecordRequest;
import com.caloriestracker.system.dto.response.health.BodyRecordResponse;
import com.caloriestracker.system.dto.response.health.ProgressPointResponse;

import java.util.List;

public interface BodyRecordService {

    BodyRecordResponse add(Long userId, CreateBodyRecordRequest request);

    List<ProgressPointResponse> getProgress(Long userId);
}