package com.universidad.cesarsoft.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankTransferPayment implements PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        try {
            // Simulación de procesamiento de transferencia bancaria
            log.info("Procesando transferencia bancaria por ${}", amount);
            Thread.sleep(1000); // Simulación de tiempo de procesamiento
            return true;
        } catch (Exception e) {
            log.error("Error procesando transferencia bancaria", e);
            return false;
        }
    }
} 