package com.universidad.cesarsoft.factory;

import com.universidad.cesarsoft.model.BankTransferPayment;
import com.universidad.cesarsoft.model.PaymentMethod;

// Factory para crear instancias de pago por transferencia bancaria
public class BankTransferPaymentFactory implements PaymentMethodFactory {
    @Override
    public PaymentMethod createPaymentMethod() {
        return new BankTransferPayment();
    }
}