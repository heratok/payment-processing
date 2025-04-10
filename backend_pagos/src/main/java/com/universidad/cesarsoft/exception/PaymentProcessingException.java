package com.universidad.cesarsoft.exception;

// Excepción personalizada para errores de procesamiento de pago
public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException(String message) {
        super(message);
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}