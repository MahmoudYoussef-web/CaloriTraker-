package com.caloriestracker.system.repository;

import com.caloriestracker.system.entity.Image;
import com.caloriestracker.system.enums.ImageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository
        extends JpaRepository<Image, Long> {

    Page<Image> findByUserIdOrderByUploadedAtDesc(
            Long userId,
            Pageable pageable
    );

    Page<Image> findByUserIdAndFavoriteTrueOrderByUploadedAtDesc(
            Long userId,
            Pageable pageable
    );

    Page<Image> findByUserIdAndStatusOrderByUploadedAtDesc(
            Long userId,
            ImageStatus status,
            Pageable pageable
    );
}