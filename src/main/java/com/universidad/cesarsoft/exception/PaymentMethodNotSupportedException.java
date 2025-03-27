package com.universidad.cesarsoft.exception;

public class PaymentMethodNotSupportedException extends RuntimeException {
    public PaymentMethodNotSupportedException(String message) {
        super(message);
    }
} 