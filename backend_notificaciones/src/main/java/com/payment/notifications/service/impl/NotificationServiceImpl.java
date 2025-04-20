package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.model.EmailNotification;
import com.payment.notifications.service.NotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.payment.notifications.config.TwilioConfig;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private TwilioConfig twilioConfig;

    private final EmailNotificationService emailService;
    private final WhatsAppNotificationService whatsAppService;

    @Autowired
    public NotificationServiceImpl(
            EmailNotificationService emailService,
            WhatsAppNotificationService whatsAppService) {
        this.emailService = emailService;
        this.whatsAppService = whatsAppService;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        switch (request.getType()) {
            case "EMAIL":
                emailService.sendNotification(request);
                break;
            case "WHATSAPP":
                whatsAppService.sendNotification(request);
                break;
            default:
                log.warn("Tipo de notificación no soportado: {}", request.getType());
        }
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber("whatsapp:" + twilioConfig.getWhatsappFrom()),
                    messageBody)
                    .create();
            
            log.info("Mensaje enviado con SID: {}", message.getSid());
        } catch (Exception e) {
            log.error("Error al enviar mensaje de WhatsApp: {}", e.getMessage());
            throw new RuntimeException("Error al enviar notificación por WhatsApp", e);
        }
    }

    @Override
    public void sendPaymentNotification(String phoneNumber, String amount, String status) {
        whatsAppService.sendPaymentNotification(phoneNumber, amount, status);
    }

    @Override
    public void sendEmailNotification(EmailNotification notification) {
        emailService.sendEmailNotification(notification);
    }
} 