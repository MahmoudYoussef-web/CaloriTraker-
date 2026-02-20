package com.caloriestracker.system.service.deficit;

import com.caloriestracker.system.dto.request.deficit.CalorieDeficitRequest;

public interface DeficitService {

    void setDeficit(Long userId, CalorieDeficitRequest request);

}