package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.model.EmailNotification;
import com.payment.notifications.service.NotificationService;
import com.payment.notifications.service.WhatsAppService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockWhatsAppService implements NotificationService, WhatsAppService {

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"WHATSAPP".equals(request.getType())) {
            return;
        }

        String message = String.format(
            "SIMULACIÓN - Mensaje de WhatsApp que se enviaría:\n" +
            "Para: %s\n" +
            "🧾 *Notificación de pago*\n\n" +
            "Monto: $%.2f\n" +
            "Método: %s\n" +
            "Estado: %s\n" +
            "ID de transacción: %s\n\n" +
            "%s",
            request.getRecipient(),
            request.getPaymentDetails().getAmount(),
            request.getPaymentDetails().getPaymentMethod(),
            request.getPaymentDetails().getStatus(),
            request.getPaymentDetails().getTransactionId(),
            request.getMessage()
        );

        log.info(message);
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        log.info("Mensaje de WhatsApp simulado enviado a: {}", to);
        log.info("Contenido: {}", messageBody);
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

    @Override
    public void sendEmailNotification(EmailNotification notification) {
        throw new UnsupportedOperationException("Método no soportado para notificaciones por WhatsApp");
    }
} 