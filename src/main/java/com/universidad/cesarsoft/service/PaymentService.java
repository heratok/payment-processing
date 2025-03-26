package com.universidad.cesarsoft.service;

import com.universidad.cesarsoft.factory.*;
import com.universidad.cesarsoft.model.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Servicio principal para procesar pagos con diferentes métodos
@Service
@Slf4j
public class PaymentService {
    private final Map<String, PaymentMethodFactory> paymentFactories;

    public PaymentService() {
        // Inicialización de fábricas de métodos de pago
        paymentFactories = new ConcurrentHashMap<>();
        paymentFactories.put("creditcard", new CreditCardPaymentFactory());
        paymentFactories.put("paypal", new PayPalPaymentFactory());
        paymentFactories.put("banktransfer", new BankTransferPaymentFactory());
    }

    // Método para procesar pagos según el método seleccionado
    public boolean processPayment(double amount, String paymentMethodType) {
        PaymentMethodFactory factory = paymentFactories.get(
                paymentMethodType.toLowerCase()
        );

        if (factory == null) {
            log.error("Método de pago no soportado: {}", paymentMethodType);
            return false;
        }

        try {
            PaymentMethod paymentMethod = factory.createPaymentMethod();
            boolean result = paymentMethod.processPayment(amount);

            log.info("Pago procesado: método={}, monto={}, resultado={}",
                    paymentMethodType, amount, result);

            return result;
        } catch (Exception e) {
            log.error("Error procesando pago", e);
            return false;
        }
    }

    // Método para registrar nuevos métodos de pago dinámicamente
    public void registerPaymentMethod(
            String methodName,
            PaymentMethodFactory factory
    ) {
        paymentFactories.put(methodName.toLowerCase(), factory);
    }
}