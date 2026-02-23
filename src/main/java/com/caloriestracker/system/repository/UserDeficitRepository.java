package com.caloriestracker.system.repository;

import com.caloriestracker.system.entity.UserDeficit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDeficitRepository
        extends JpaRepository<UserDeficit, Long> {

    @Query("""
        SELECT d
        FROM UserDeficit d
        WHERE d.user.id = :userId
    """)
    Optional<UserDeficit> findByUserId(@Param("userId") Long userId);
}