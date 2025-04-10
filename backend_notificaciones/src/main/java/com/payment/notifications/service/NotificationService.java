package com.payment.notifications.service;

import com.payment.notifications.model.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest request);
    void sendWhatsAppMessage(String to, String messageBody);
    void sendPaymentNotification(String phoneNumber, String amount, String status);
} 