package com.payment.notifications.service;

public interface WhatsAppService {
    void sendWhatsAppMessage(String to, String messageBody);
    void sendPaymentNotification(String phoneNumber, String amount, String status);
} 