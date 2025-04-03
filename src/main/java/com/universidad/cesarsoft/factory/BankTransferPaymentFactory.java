package com.universidad.cesarsoft.factory;

import com.universidad.cesarsoft.model.PaymentMethod;
import com.universidad.cesarsoft.model.BankTransferPayment;

public class BankTransferPaymentFactory implements PaymentMethodFactory {
    @Override
    public PaymentMethod createPaymentMethod() {
        return new BankTransferPayment();
    }
} 