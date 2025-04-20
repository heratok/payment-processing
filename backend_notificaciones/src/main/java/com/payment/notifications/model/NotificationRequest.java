package com.payment.notifications.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class NotificationRequest {
    private String type; // "EMAIL", "SMS", "WHATSAPP"
    private String recipient; // email, número de teléfono, etc.
    private String subject;
    private String message;
    private PaymentDetails paymentDetails;

    @Data
    public static class PaymentDetails {
        private double amount;
        private String paymentMethod;
        private String date; // Cambiado a String
        private String transactionId;
        private String status;

        public LocalDateTime getDateAsLocalDateTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDateTime.parse(date + "T00:00:00", formatter);
        }
    }
} 