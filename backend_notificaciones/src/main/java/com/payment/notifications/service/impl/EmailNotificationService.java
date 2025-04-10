package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"EMAIL".equals(request.getType())) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getRecipient());
        message.setSubject(request.getSubject());
        
        String emailBody = String.format(
            "Estimado usuario,\n\n" +
            "Se ha procesado su pago exitosamente.\n\n" +
            "Detalles del pago:\n" +
            "Monto: $%.2f\n" +
            "Método de pago: %s\n" +
            "Estado: %s\n" +
            "ID de transacción: %s\n\n" +
            "Gracias por su preferencia.",
            request.getPaymentDetails().getAmount(),
            request.getPaymentDetails().getPaymentMethod(),
            request.getPaymentDetails().getStatus(),
            request.getPaymentDetails().getTransactionId()
        );
        
        message.setText(emailBody);
        
        try {
            mailSender.send(message);
            log.info("Email enviado exitosamente a: {}", request.getRecipient());
        } catch (Exception e) {
            log.error("Error al enviar email: {}", e.getMessage());
            throw new RuntimeException("Error al enviar email", e);
        }
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        // No implementado para email
        throw new UnsupportedOperationException("Método no soportado para notificaciones por email");
    }

    @Override
    public void sendPaymentNotification(String phoneNumber, String amount, String status) {
        // No implementado para email
        throw new UnsupportedOperationException("Método no soportado para notificaciones por email");
    }
} 