package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.model.EmailNotification;
import com.payment.notifications.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.extern.slf4j.Slf4j;
import java.time.format.DateTimeFormatter;

@Slf4j
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"EMAIL".equals(request.getType())) {
            return;
        }

        String messageBody = request.getMessage() != null ? request.getMessage() : 
            String.format(
                "Estimado usuario,\n\n" +
                "Se ha procesado su pago exitosamente.\n\n" +
                "Detalles del pago:\n" +
                "Monto: $%.2f\n" +
                "Método de pago: %s\n" +
                "Fecha: %s\n" +
                "ID de transacción: %s\n" +
                "Estado: %s\n\n" +
                "Gracias por su preferencia.",
                request.getPaymentDetails().getAmount(),
                request.getPaymentDetails().getPaymentMethod(),
                request.getPaymentDetails().getDate(),
                request.getPaymentDetails().getTransactionId(),
                request.getPaymentDetails().getStatus()
            );

        EmailNotification notification = new EmailNotification.Builder(
                request.getRecipient(),
                request.getSubject(),
                messageBody)
                .priority("alta")
                .build();

        sendEmailNotification(notification);
    }

    @Override
    public void sendEmailNotification(EmailNotification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());
        
        if (notification.getCc() != null && !notification.getCc().isEmpty()) {
            message.setCc(notification.getCc().toArray(new String[0]));
        }
        
        if (notification.getBcc() != null && !notification.getBcc().isEmpty()) {
            message.setBcc(notification.getBcc().toArray(new String[0]));
        }
        
        try {
            mailSender.send(message);
            log.info("Email enviado exitosamente a: {}", notification.getTo());
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