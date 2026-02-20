package com.caloriestracker.system.util;

import com.caloriestracker.system.entity.User;
import com.caloriestracker.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UserRepository userRepo;

    public Long getUserId(Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated");
        }

        Object principal = auth.getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            throw new RuntimeException("Invalid principal");
        }

        String email = userDetails.getUsername();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return user.getId();
    }
}