package com.universidad.cesarsoft.config;

import com.universidad.cesarsoft.factory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuración de fábricas de métodos de pago como beans de Spring
@Configuration
public class PaymentConfiguration {

    // Bean para fábrica de pagos con tarjeta de crédito
    @Bean
    public PaymentMethodFactory creditCardPaymentFactory() {
        return new CreditCardPaymentFactory();
    }

    // Bean para fábrica de pagos con PayPal
    @Bean
    public PaymentMethodFactory payPalPaymentFactory() {
        return new PayPalPaymentFactory();
    }

    // Bean para fábrica de transferencias bancarias
    @Bean
    public PaymentMethodFactory bankTransferPaymentFactory() {
        return new BankTransferPaymentFactory();
    }
}