package com.universidad.cesarsoft.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentCalculatorService {
    // Constantes para tarjeta de crédito
    private static final double CREDIT_CARD_COMMISSION = 0.05;
    private static final double CREDIT_CARD_FEE = 10.0;
    private static final double CREDIT_CARD_THRESHOLD = 1000.0;

    // Constantes para tarjeta de débito
    private static final double DEBIT_CARD_COMMISSION = 0.01;
    private static final double DEBIT_CARD_FEE = 5.0;
    private static final double DEBIT_CARD_THRESHOLD = 500.0;

    // Constantes para PayPal
    private static final double PAYPAL_COMMISSION = 0.02;
    private static final double PAYPAL_FEE = 7.0;
    private static final double PAYPAL_THRESHOLD = 750.0;

    public double calculateCreditCardPayment(double amount) {
        double finalAmount = amount + (amount * CREDIT_CARD_COMMISSION);
        if (amount > CREDIT_CARD_THRESHOLD) {
            finalAmount += CREDIT_CARD_FEE;
        }
        return finalAmount;
    }

    public double calculateDebitCardPayment(double amount) {
        double finalAmount = amount + (amount * DEBIT_CARD_COMMISSION);
        if (amount > DEBIT_CARD_THRESHOLD) {
            finalAmount += DEBIT_CARD_FEE;
        }
        return finalAmount;
    }

    public double calculatePayPalPayment(double amount) {
        double finalAmount = amount + (amount * PAYPAL_COMMISSION);
        if (amount > PAYPAL_THRESHOLD) {
            finalAmount += PAYPAL_FEE;
        }
        return finalAmount;
    }
} 