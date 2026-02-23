package com.caloriestracker.system.service.ai.provider;

import com.caloriestracker.system.service.ai.client.AiResult;
import com.caloriestracker.system.service.ai.client.MockAiClient;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
@RequiredArgsConstructor
public class MockVisionProvider implements AiVisionProvider {

    private final MockAiClient mockClient;

    @Override
    public AiResult analyze(MultipartFile file) {

        return mockClient.analyze(file);
    }
}