package com.caloriestracker.system.service.auth;

import com.caloriestracker.system.dto.request.auth.LoginRequest;
import com.caloriestracker.system.dto.request.auth.RegisterRequest;
import com.caloriestracker.system.dto.response.auth.AuthResponse;
import com.caloriestracker.system.entity.User;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.exception.UnauthorizedException;
import com.caloriestracker.system.repository.UserRepository;
import com.caloriestracker.system.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {

        validate(request.getEmail(), request.getPassword());

        String email = request.getEmail().toLowerCase().trim();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password")
                );

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return buildResponse(user);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        validate(request.getEmail(), request.getPassword());

        String email = request.getEmail().toLowerCase().trim();

        if (userRepo.existsByEmail(email)) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .email(email)
                .password(encoder.encode(request.getPassword()))
                .build();

        userRepo.save(user);

        return buildResponse(user);
    }


    private void validate(String email, String password) {

        if (email == null || password == null ||
        email.isBlank() || password.isBlank()) {

            throw new BadRequestException("Email and password required");
        }
    }

    private AuthResponse buildResponse(User user) {

        String token = jwtService.generate(user);

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                token
        );
    }
}