package com.payment.notifications.factory;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;

public interface NotificationFactory {
    NotificationService createNotificationService();
    boolean supports(NotificationRequest request);
} 