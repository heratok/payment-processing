package com.universidad.cesarsoft.model;

// Implementación de pago con PayPal
public class PayPalPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Procesando pago con PayPal: $" + amount);
        return true;
    }
}