package com.caloriestracker.system.service.health.bmi;

import com.caloriestracker.system.dto.request.health.BmiRequest;
import com.caloriestracker.system.dto.response.health.BmiResponse;

public interface BmiService {

    BmiResponse calculate(BmiRequest request);
}