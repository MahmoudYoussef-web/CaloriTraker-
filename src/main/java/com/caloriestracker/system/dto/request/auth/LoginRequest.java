package com.caloriestracker.system.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginRequest {

    @NotBlank
    private String username; // username OR email

    @NotBlank
    private String password;
}
