package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
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
        String message = String.format(
            "SIMULACIÓN - Mensaje de WhatsApp que se enviaría:\n" +
            "Para: %s\n" +
            "Mensaje: %s",
            to,
            messageBody
        );
        
        log.info(message);
    }

    @Override
    public void sendPaymentNotification(String phoneNumber, String amount, String status) {
        String message = String.format(
            "SIMULACIÓN - Notificación de pago que se enviaría por WhatsApp:\n" +
            "Para: %s\n" +
            "Tu pago por %s ha sido %s. Gracias por usar nuestro servicio.",
            phoneNumber,
            amount,
            status
        );
        
        log.info(message);
    }
} 