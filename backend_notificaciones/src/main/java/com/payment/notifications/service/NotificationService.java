package com.payment.notifications.service;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.model.EmailNotification;

public interface NotificationService {
    void sendNotification(NotificationRequest request);
    void sendWhatsAppMessage(String to, String messageBody);
    void sendPaymentNotification(String phoneNumber, String amount, String status);
    void sendEmailNotification(EmailNotification notification);
} 