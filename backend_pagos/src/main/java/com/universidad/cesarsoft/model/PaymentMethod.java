package com.universidad.cesarsoft.model;

// Interfaz que define el contrato para métodos de pago
public interface PaymentMethod {
    boolean processPayment(double amount);
}