package com.caloriestracker.system.service.security;

import com.caloriestracker.system.entity.User;
import org.springframework.stereotype.Service;


@Service
public interface JwtService {

    String generate(User user);

    Long extractUserId(String token);

    boolean validate(String token);
}