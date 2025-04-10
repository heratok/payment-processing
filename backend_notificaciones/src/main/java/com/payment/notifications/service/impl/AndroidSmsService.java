package com.payment.notifications.service.impl;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import com.payment.notifications.service.WhatsAppService;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class AndroidSmsService implements NotificationService, WhatsAppService {

    private static final String ADB_PATH = "adb"; // Asume que adb está en el PATH

    @Override
    public void sendNotification(NotificationRequest request) {
        if (!"SMS".equals(request.getType())) {
            return;
        }

        String message = String.format(
            "Notificación de pago:\n" +
            "Monto: $%.2f\n" +
            "Método: %s\n" +
            "Estado: %s\n" +
            "ID: %s",
            request.getPaymentDetails().getAmount(),
            request.getPaymentDetails().getPaymentMethod(),
            request.getPaymentDetails().getStatus(),
            request.getPaymentDetails().getTransactionId()
        );

        sendSms(request.getRecipient(), message);
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageBody) {
        // Para WhatsApp, usaremos la aplicación de Android directamente
        try {
            String command = String.format(
                "%s shell am start -a android.intent.action.SEND " +
                "-t text/plain " +
                "--es android.intent.extra.TEXT %s " +
                "-p com.whatsapp",
                ADB_PATH,
                escapeMessage(messageBody)
            );

            executeCommand(command);
            log.info("Comando de WhatsApp enviado al dispositivo Android");
        } catch (Exception e) {
            log.error("Error al enviar mensaje de WhatsApp: {}", e.getMessage());
            throw new RuntimeException("Error al enviar WhatsApp", e);
        }
    }

    @Override
    public void sendPaymentNotification(String phoneNumber, String amount, String status) {
        String message = String.format(
            "Tu pago por %s ha sido %s. Gracias por usar nuestro servicio.",
            amount,
            status
        );
        sendSms(phoneNumber, message);
    }

    private void sendSms(String phoneNumber, String message) {
        try {
            // Comando para enviar SMS usando ADB
            String command = String.format(
                "%s shell service call isms 7 i32 0 s16 %s s16 null s16 %s",
                ADB_PATH,
                phoneNumber,
                escapeMessage(message)
            );

            executeCommand(command);
            log.info("SMS enviado al número: {}", phoneNumber);
        } catch (Exception e) {
            log.error("Error al enviar SMS: {}", e.getMessage());
            throw new RuntimeException("Error al enviar SMS", e);
        }
    }

    private String executeCommand(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Error ejecutando comando ADB. Código de salida: " + exitCode);
        }

        return output.toString();
    }

    private String escapeMessage(String message) {
        return "'" + message.replace("'", "\\'") + "'";
    }
} 