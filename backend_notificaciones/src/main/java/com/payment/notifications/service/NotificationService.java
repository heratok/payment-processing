package com.payment.notifications.service;

import com.payment.notifications.factory.NotificationFactory;
import com.payment.notifications.model.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {
    
    private final Map<String, NotificationFactory> notificationFactories;

    public NotificationService(Map<String, NotificationFactory> notificationFactories) {
        this.notificationFactories = notificationFactories;
    }

    public void sendNotification(NotificationRequest request) {
        NotificationFactory factory = notificationFactories.get(request.getType().toLowerCase() + "NotificationFactory");
        if (factory == null) {
            throw new IllegalArgumentException("Tipo de notificación no soportado: " + request.getType());
        }
        factory.sendNotification(request);
    }
} 