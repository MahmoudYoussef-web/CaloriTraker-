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
    public UserDetails loadUserByUsername(String identifier)
            throws UsernameNotFoundException {

        User user = userRepo
                .findByEmailOrUsername(identifier, identifier)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return build(user);
    }


    public UserDetails loadUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return build(user);
    }


    private UserDetails build(User user) {

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}