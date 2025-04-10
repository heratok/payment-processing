package com.payment.notifications.factory;

import com.payment.notifications.model.NotificationRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationFactory implements NotificationFactory {
    
    private final JavaMailSender mailSender;

    public EmailNotificationFactory(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getRecipient());
        message.setSubject(request.getSubject());
        
        String emailBody = String.format(
            "Estimado usuario,\n\n" +
            "Se ha procesado su pago exitosamente.\n\n" +
            "Detalles del pago:\n" +
            "Monto: $%.2f\n" +
            "Método de pago: %s\n" +
            "Fecha: %s\n" +
            "ID de transacción: %s\n\n" +
            "Gracias por su preferencia.",
            request.getPaymentDetails().getAmount(),
            request.getPaymentDetails().getPaymentMethod(),
            request.getPaymentDetails().getDate(),
            request.getPaymentDetails().getTransactionId()
        );
        
        message.setText(emailBody);
        mailSender.send(message);
    }
} 