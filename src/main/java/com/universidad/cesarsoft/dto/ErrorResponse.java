package com.universidad.cesarsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO para manejar respuestas de error
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;
}