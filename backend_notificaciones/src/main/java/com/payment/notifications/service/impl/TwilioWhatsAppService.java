package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import com.payment.notifications.service.WhatsAppService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

@Slf4j
public class TwilioWhatsAppService implements NotificationService, WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String twilioWhatsAppNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"WHATSAPP".equals(request.getType())) {
            return;
        }

        String message = String.format(
            "🧾 *Notificación de pago*\n\n" +
            "Monto: $%.2f\n" +
            "Método: %s\n" +
            "Estado: %s\n" +
            "ID de transacción: %s\n\n" +
            "%s",
            request.getPaymentDetails().getAmount(),
            request.getPaymentDetails().getPaymentMethod(),
            request.getPaymentDetails().getStatus(),
            request.getPaymentDetails().getTransactionId(),
            request.getMessage()
        );

        sendWhatsAppMessage(request.getRecipient(), message);
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        try {
            Message message = Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + twilioWhatsAppNumber),
                messageBody
            ).create();
            
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