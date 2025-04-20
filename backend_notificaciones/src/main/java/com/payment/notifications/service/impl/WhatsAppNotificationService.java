package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.model.WhatsAppNotification;
import com.payment.notifications.model.EmailNotification;
import com.payment.notifications.service.NotificationService;
import com.payment.notifications.service.WhatsAppService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.payment.notifications.config.TwilioConfig;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;

@Slf4j
public class WhatsAppNotificationService implements NotificationService, WhatsAppService {

    private final TwilioConfig twilioConfig;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public WhatsAppNotificationService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"WHATSAPP".equals(request.getType())) {
            return;
        }

        String messageBody = request.getMessage() != null ? request.getMessage() : 
            String.format(
                "🧾 *Notificación de pago*\n\n" +
                "Monto: $%.2f\n" +
                "Método: %s\n" +
                "Fecha: %s\n" +
                "ID de transacción: %s\n" +
                "Estado: %s",
                request.getPaymentDetails().getAmount(),
                request.getPaymentDetails().getPaymentMethod(),
                request.getPaymentDetails().getDate(),
                request.getPaymentDetails().getTransactionId(),
                request.getPaymentDetails().getStatus()
            );

        WhatsAppNotification notification = new WhatsAppNotification.Builder(
                request.getRecipient(),
                messageBody)
                .language("es")
                .interactiveButtons(Arrays.asList("Ver detalles", "Contactar soporte"))
                .build();

        sendWhatsAppMessage(notification);
    }

    private void sendWhatsAppMessage(WhatsAppNotification notification) {
        try {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + notification.getPhoneNumber()),
                    new PhoneNumber("whatsapp:" + twilioConfig.getWhatsappFrom()),
                    notification.getMessage())
                    .create();
            
            log.info("Mensaje de WhatsApp enviado con SID: {}", message.getSid());
        } catch (Exception e) {
            log.error("Error al enviar mensaje de WhatsApp: {}", e.getMessage());
            throw new RuntimeException("Error al enviar notificación por WhatsApp", e);
        }
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        WhatsAppNotification notification = new WhatsAppNotification.Builder(to, messageBody)
                .language("es")
                .build();
        sendWhatsAppMessage(notification);
    }

    @Override
    public void sendPaymentNotification(String phoneNumber, String amount, String status) {
        WhatsAppNotification notification = new WhatsAppNotification.Builder(
                phoneNumber,
                String.format("Tu pago por %s ha sido %s. Gracias por usar nuestro servicio.", amount, status))
                .language("es")
                .build();

        sendWhatsAppMessage(notification);
    }

    @Override
    public void sendEmailNotification(EmailNotification notification) {
        throw new UnsupportedOperationException("Método no soportado para notificaciones por WhatsApp");
    }
} 