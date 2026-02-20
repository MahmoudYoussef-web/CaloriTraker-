package com.caloriestracker.system.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ApiErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String message;
    private final String errorCode;
    private final String path;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors;

    public ApiErrorResponse(
            int status,
            String message,
            String errorCode,
            String path
    ) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.errors = null;
    }

    public ApiErrorResponse(
            int status,
            String message,
            String errorCode,
            String path,
            Map<String, String> errors
    ) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}