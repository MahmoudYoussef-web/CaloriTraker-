package com.caloriestracker.system.service.ai.client;

import org.springframework.web.multipart.MultipartFile;

public interface AiClient {

    AiResult analyze(MultipartFile file);
}