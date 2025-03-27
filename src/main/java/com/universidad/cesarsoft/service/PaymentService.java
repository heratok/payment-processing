package com.universidad.cesarsoft.service;

import com.universidad.cesarsoft.factory.*;
import com.universidad.cesarsoft.model.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Servicio principal para procesar pagos con diferentes métodos
@Service
@Slf4j
public class PaymentService {
    private final Map<String, PaymentMethodFactory> paymentFactories;
    private final PaymentCalculatorService calculatorService;

    @Autowired
    public PaymentService(PaymentCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
        // Inicialización de fábricas de métodos de pago
        paymentFactories = new ConcurrentHashMap<>();
        paymentFactories.put("creditcard", new CreditCardPaymentFactory());
        paymentFactories.put("paypal", new PayPalPaymentFactory());
        paymentFactories.put("debitcard", new DebitCardPaymentFactory());
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
            // Calcular el monto final según el tipo de pago
            double finalAmount = calculateFinalAmount(amount, paymentMethodType.toLowerCase());
            
            PaymentMethod paymentMethod = factory.createPaymentMethod();
            boolean result = paymentMethod.processPayment(finalAmount);

            log.info("Pago procesado: método={}, monto original={}, monto final={}, resultado={}",
                    paymentMethodType, amount, finalAmount, result);

            return result;
        } catch (Exception e) {
            log.error("Error procesando pago", e);
            return false;
        }
    }

    private double calculateFinalAmount(double amount, String paymentMethodType) {
        return switch (paymentMethodType) {
            case "creditcard" -> calculatorService.calculateCreditCardPayment(amount);
            case "debitcard" -> calculatorService.calculateDebitCardPayment(amount);
            case "paypal" -> calculatorService.calculatePayPalPayment(amount);
            default -> amount;
        };
    }

    // Método para registrar nuevos métodos de pago dinámicamente
    public void registerPaymentMethod(
            String methodName,
            PaymentMethodFactory factory
    ) {
        paymentFactories.put(methodName.toLowerCase(), factory);
    }
}