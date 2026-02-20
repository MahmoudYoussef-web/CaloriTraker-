package com.caloriestracker.system.repository;

import com.caloriestracker.system.entity.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailySummaryRepository
        extends JpaRepository<DailySummary, Long> {

    Optional<DailySummary> findByUserIdAndDate(
            Long userId,
            LocalDate date

    );

    List<DailySummary> findByUserIdAndDateBetweenOrderByDateAsc(
            Long userId,
            LocalDate start,
            LocalDate end
    );

    boolean existsByUserIdAndDate(Long userId, LocalDate date);

    void deleteByUserIdAndDate(Long userId, LocalDate date);
}