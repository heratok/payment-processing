package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import com.payment.notifications.service.WhatsAppService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.payment.notifications.config.TwilioConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhatsAppNotificationService implements NotificationService, WhatsAppService {

    private final TwilioConfig twilioConfig;

    public WhatsAppNotificationService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"WHATSAPP".equals(request.getType())) {
            return;
        }

        sendWhatsAppMessage(request.getRecipient(), request.getMessage());
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber("whatsapp:" + twilioConfig.getWhatsappFrom()),
                    messageBody)
                    .create();
            
            log.info("Mensaje de WhatsApp enviado con SID: {}", message.getSid());
        } catch (Exception e) {
            log.error("Error al enviar mensaje de WhatsApp: {}", e.getMessage());
            throw new RuntimeException("Error al enviar notificación por WhatsApp", e);
        }
    }

    @Override
    public void sendPaymentNotification(String phoneNumber, String amount, String status) {
        String message = String.format(
            "Tu pago por %s ha sido %s. Gracias por usar nuestro servicio.",
            amount,
            status
        );
        sendWhatsAppMessage(phoneNumber, message);
    }
} 