package com.caloriestracker.system.service.profile;

import com.caloriestracker.system.dto.request.profile.UserProfileRequest;
import com.caloriestracker.system.dto.response.profile.UserFullProfileResponse;
import com.caloriestracker.system.dto.response.profile.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse getProfile(Long userId);

    UserProfileResponse updateProfile(Long userId, UserProfileRequest request);

    UserFullProfileResponse getFullProfile(Long userId);

}