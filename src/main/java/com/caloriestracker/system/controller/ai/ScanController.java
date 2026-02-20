package com.caloriestracker.system.controller.ai;

import com.caloriestracker.system.dto.response.ai.AiAnalyzeResponse;
import com.caloriestracker.system.dto.response.ai.ImageResponse;
import com.caloriestracker.system.enums.ImageStatus;
import com.caloriestracker.system.exception.BadRequestException;
import com.caloriestracker.system.service.ai.AiService;
import com.caloriestracker.system.service.image.ImageService;
import com.caloriestracker.system.util.AuthUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/scan")
@RequiredArgsConstructor
public class ScanController {

    private final AiService aiService;
    private final ImageService imageService;
    private final AuthUtils authUtils;

    @PostMapping(
            value = "/analyze/{mealId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AiAnalyzeResponse> analyze(
            @PathVariable Long mealId,
            @RequestPart("file") MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Image file is required");
        }

        return ResponseEntity.ok(
                aiService.analyze(mealId, file)
        );
    }

    @GetMapping("/status/{imageId}")
    public ResponseEntity<ImageStatus> getStatus(
            @PathVariable Long imageId
    ) {

        return ResponseEntity.ok(
                aiService.getStatus(imageId)
        );
    }

    @GetMapping("/gallery")
    public ResponseEntity<Page<ImageResponse>> getGallery(
            Authentication auth,
            Pageable pageable
    ) {

        Long userId = authUtils.getUserId(auth);

        return ResponseEntity.ok(
                imageService.getGallery(userId, pageable)
        );
    }

    @PatchMapping("/favorite/{imageId}")
    public ResponseEntity<Void> toggleFavorite(
            @PathVariable Long imageId
    ) {

        imageService.toggleFavorite(imageId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long imageId
    ) {

        imageService.delete(imageId);

        return ResponseEntity.noContent().build();
    }
}