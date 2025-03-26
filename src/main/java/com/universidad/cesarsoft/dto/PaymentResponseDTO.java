package com.universidad.cesarsoft.dto;

import lombok.Data;

// DTO para responder después de procesar un pago
@Data
public class PaymentResponseDTO {
    private boolean success;
    private String message;
    private String transactionId;
}