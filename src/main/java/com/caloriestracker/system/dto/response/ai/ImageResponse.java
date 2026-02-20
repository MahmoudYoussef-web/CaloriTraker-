package com.caloriestracker.system.dto.response.ai;

import com.caloriestracker.system.enums.ImageStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageResponse {

    private Long id;

    private String path;

    private LocalDateTime uploadedAt;

    private ImageStatus status;

    private boolean favorite;
    private  Long mealItemId;
}