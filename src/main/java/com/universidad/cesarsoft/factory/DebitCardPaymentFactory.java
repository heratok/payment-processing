package com.universidad.cesarsoft.factory;

import com.universidad.cesarsoft.model.DebitCardPayment;
import com.universidad.cesarsoft.model.PaymentMethod;

// Factory para crear instancias de pago con tarjeta de débito
public class DebitCardPaymentFactory implements PaymentMethodFactory {
    @Override
    public PaymentMethod createPaymentMethod() {
        return new DebitCardPayment();
    }
} 