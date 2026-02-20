package com.caloriestracker.system.service.image;

import com.caloriestracker.system.dto.response.ai.ImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageService {

    Page<ImageResponse> getGallery(Long userId, Pageable pageable);

    void toggleFavorite(Long imageId);

    void delete(Long imageId);
}