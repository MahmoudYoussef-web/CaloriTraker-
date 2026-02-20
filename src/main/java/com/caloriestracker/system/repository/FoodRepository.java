package com.caloriestracker.system.repository;

import com.caloriestracker.system.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository
        extends JpaRepository<Food, Long> {

    Optional<Food> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}