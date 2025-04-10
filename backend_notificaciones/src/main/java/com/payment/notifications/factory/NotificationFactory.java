package com.payment.notifications.factory;

import com.payment.notifications.model.NotificationRequest;

public interface NotificationFactory {
    void sendNotification(NotificationRequest request);
} 