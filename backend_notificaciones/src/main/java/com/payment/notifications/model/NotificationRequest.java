package com.payment.notifications.model;

import lombok.Data;

@Data
public class NotificationRequest {
    private String type; // "EMAIL", "SMS", "WHATSAPP"
    private String recipient; // email, número de teléfono, etc.
    private String subject;
    private String message;
    private PaymentDetails paymentDetails;
} 