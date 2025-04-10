package com.payment.notifications.config;

import com.payment.notifications.service.WhatsAppService;
import com.payment.notifications.service.impl.WhatsAppNotificationService;
import com.payment.notifications.service.impl.EmailNotificationService;
import com.payment.notifications.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class NotificationConfig {

    @Bean("whatsAppNotificationService")
    public WhatsAppService whatsAppService(TwilioConfig twilioConfig) {
        return new WhatsAppNotificationService(twilioConfig);
    }

    @Bean("emailNotificationService")
    public NotificationService emailService(JavaMailSender mailSender) {
        return new EmailNotificationService(mailSender);
    }
} 