package com.caloriestracker.system.repository;

import com.caloriestracker.system.entity.User;
import com.caloriestracker.system.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository
        extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

    Optional<UserProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}