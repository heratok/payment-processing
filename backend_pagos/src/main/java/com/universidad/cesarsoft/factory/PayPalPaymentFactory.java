package com.universidad.cesarsoft.factory;

import com.universidad.cesarsoft.model.PayPalPayment;
import com.universidad.cesarsoft.model.PaymentMethod;

// Factory para crear instancias de pago con PayPal
public class PayPalPaymentFactory implements PaymentMethodFactory {
    @Override
    public PaymentMethod createPaymentMethod() {
        return new PayPalPayment();
    }
}