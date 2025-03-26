package com.universidad.cesarsoft.factory;

import com.universidad.cesarsoft.model.PaymentMethod;

// Interfaz para crear métodos de pago
public interface PaymentMethodFactory {
    PaymentMethod createPaymentMethod();
}