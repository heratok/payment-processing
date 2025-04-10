package com.payment.notifications.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
    private double amount;
    private String paymentMethod;
    private String status;
    private String transactionId;
} 