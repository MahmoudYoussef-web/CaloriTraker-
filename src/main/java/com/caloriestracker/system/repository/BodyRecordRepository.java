package com.caloriestracker.system.repository;

import com.caloriestracker.system.entity.BodyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BodyRecordRepository
        extends JpaRepository<BodyRecord, Long> {

    List<BodyRecord> findByUserIdAndRecordDateBetweenOrderByRecordDateAsc(
            Long userId,
            LocalDate start,
            LocalDate end
    );

    Optional<BodyRecord> findTopByUserIdOrderByRecordDateDesc(Long userId);

    boolean existsByUserIdAndRecordDate(
            Long userId,
            LocalDate recordDate
    );
}