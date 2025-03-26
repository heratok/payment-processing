package com.universidad.cesarsoft.model;

// Implementación de pago por transferencia bancaria
public class BankTransferPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Procesando transferencia bancaria: $" + amount);
        return true;
    }
}