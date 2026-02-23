package com.caloriestracker.system.service.auth;

import com.caloriestracker.system.dto.request.auth.LoginRequest;
import com.caloriestracker.system.dto.request.auth.RegisterRequest;
import com.caloriestracker.system.dto.response.auth.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}