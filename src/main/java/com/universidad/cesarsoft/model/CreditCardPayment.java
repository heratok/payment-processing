package com.universidad.cesarsoft.model;

// Implementación de pago con tarjeta de crédito
public class CreditCardPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Procesando pago con tarjeta de crédito: $" + amount);
        return true;
    }
}