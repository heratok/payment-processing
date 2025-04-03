package com.universidad.cesarsoft.model;

// Implementación de pago con tarjeta de crédito
public class CreditCardPayment implements PaymentMethod {
    private static final double COMMISSION_RATE = 0.05; // 5% de comisión
    private static final double ADDITIONAL_FEE = 10.0;
    private static final double ADDITIONAL_FEE_THRESHOLD = 1000.0;

    @Override
    public boolean processPayment(double amount) {
        double finalAmount = amount + (amount * COMMISSION_RATE);
        
        if (amount > ADDITIONAL_FEE_THRESHOLD) {
            finalAmount += ADDITIONAL_FEE;
        }

        System.out.println("Procesando pago con tarjeta de crédito: $" + finalAmount);
        return true;
    }
}