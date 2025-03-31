package com.universidad.cesarsoft.model;

// Implementación de pago con tarjeta de débito
public class DebitCardPayment implements PaymentMethod {
    private static final double COMMISSION_RATE = 0.01; // 1% de comisión
    private static final double ADDITIONAL_FEE = 5.0;
    private static final double ADDITIONAL_FEE_THRESHOLD = 500.0;

    @Override
    public boolean processPayment(double amount) {
        double finalAmount = amount + (amount * COMMISSION_RATE);
        
        if (amount > ADDITIONAL_FEE_THRESHOLD) {
            finalAmount += ADDITIONAL_FEE;
        }

        System.out.println("Procesando pago con tarjeta de débito: $" + finalAmount);
        return true;
    }
} 