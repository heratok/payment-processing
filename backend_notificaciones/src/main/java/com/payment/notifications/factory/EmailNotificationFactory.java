package com.payment.notifications.factory;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationFactory implements NotificationFactory {

    @Autowired
    @Qualifier("emailNotificationService")
    private NotificationService emailService;

    @Override
    public NotificationService createNotificationService() {
        return emailService;
    }

    @Override
    public boolean supports(NotificationRequest request) {
        return "EMAIL".equals(request.getType());
    }
} 