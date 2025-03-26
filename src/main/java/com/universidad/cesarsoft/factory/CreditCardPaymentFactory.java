package com.universidad.cesarsoft.factory;

import com.universidad.cesarsoft.model.CreditCardPayment;
import com.universidad.cesarsoft.model.PaymentMethod;

// Factory para crear instancias de pago con tarjeta de crédito
public class CreditCardPaymentFactory implements PaymentMethodFactory {
    @Override
    public PaymentMethod createPaymentMethod() {
        return new CreditCardPayment();
    }
}