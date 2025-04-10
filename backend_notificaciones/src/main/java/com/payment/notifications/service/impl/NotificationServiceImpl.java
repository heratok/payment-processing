package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
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

    @Override
    public void sendNotification(NotificationRequest request) {
        String message = String.format("Notificación de pago:\n" +
                "Monto: $%.2f\n" +
                "Método: %s\n" +
                "Estado: %s",
                request.getPaymentDetails().getAmount(),
                request.getPaymentDetails().getPaymentMethod(),
                request.getPaymentDetails().getStatus());

        sendWhatsAppMessage(request.getRecipient(), message);
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
        String message = String.format(
            "Tu pago por %s ha sido %s. Gracias por usar nuestro servicio.",
            amount,
            status
        );
        sendWhatsAppMessage(phoneNumber, message);
    }
} 