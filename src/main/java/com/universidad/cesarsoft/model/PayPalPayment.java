package com.universidad.cesarsoft.model;

// Implementación de pago con PayPal
public class PayPalPayment implements PaymentMethod {
    private static final double COMMISSION_RATE = 0.02; // 2% de comisión
    private static final double ADDITIONAL_FEE = 7.0;
    private static final double ADDITIONAL_FEE_THRESHOLD = 750.0;

    @Override
    public boolean processPayment(double amount) {
        double finalAmount = amount + (amount * COMMISSION_RATE);
        
        if (amount > ADDITIONAL_FEE_THRESHOLD) {
            finalAmount += ADDITIONAL_FEE;
        }

        System.out.println("Procesando pago con PayPal: $" + finalAmount);
        return true;
    }
}