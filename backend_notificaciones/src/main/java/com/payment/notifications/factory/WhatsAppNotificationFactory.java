package com.payment.notifications.factory;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import com.payment.notifications.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.payment.notifications.service.impl.WhatsAppNotificationService;

@Component
public class WhatsAppNotificationFactory implements NotificationFactory {

    @Autowired
    private TwilioConfig twilioConfig;

    @Override
    public NotificationService createNotificationService() {
        return new WhatsAppNotificationService(twilioConfig);
    }

    @Override
    public boolean supports(NotificationRequest request) {
        return "WHATSAPP".equals(request.getType());
    }
} 