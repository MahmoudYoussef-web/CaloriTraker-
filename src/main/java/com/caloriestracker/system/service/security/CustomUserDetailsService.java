package com.caloriestracker.system.service.security;

import com.caloriestracker.system.entity.User;
import com.caloriestracker.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return buildUserDetails(user);
    }

    public UserDetails loadUserById(Long id)
            throws UsernameNotFoundException {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}