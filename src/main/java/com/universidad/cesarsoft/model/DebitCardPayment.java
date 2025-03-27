package com.universidad.cesarsoft.model;

// Implementación de pago con tarjeta de débito
public class DebitCardPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Procesando pago con tarjeta de débito: $" + amount);
        return true;
    }
} 