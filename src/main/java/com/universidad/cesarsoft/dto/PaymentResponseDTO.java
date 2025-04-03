package com.universidad.cesarsoft.dto;

import lombok.Data;

// DTO para responder después de procesar un pago
@Data
public class PaymentResponseDTO {
    private String transactionId;
    private boolean success;
    private String message;
    private String paymentMethod;

    public PaymentResponseDTO() {
        this.transactionId = java.util.UUID.randomUUID().toString();
    }
}