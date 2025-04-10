package com.payment.notifications.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaymentDetails {
    private double amount;
    private String paymentMethod;
    private String date;
    private String transactionId;
} 