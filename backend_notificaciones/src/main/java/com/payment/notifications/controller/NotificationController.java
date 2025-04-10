package com.payment.notifications.controller;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.factory.NotificationFactory;
import com.payment.notifications.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class NotificationController {

    private final List<NotificationFactory> notificationFactories;
    private final WhatsAppService whatsAppService;

    public NotificationController(List<NotificationFactory> notificationFactories, 
                                @Qualifier("whatsAppNotificationService") WhatsAppService whatsAppService) {
        this.notificationFactories = notificationFactories;
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            notificationFactories.stream()
                .filter(factory -> factory.supports(request))
                .findFirst()
                .ifPresent(factory -> factory.createNotificationService().sendNotification(request));
            
            return ResponseEntity.ok("Notificación enviada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al enviar la notificación: " + e.getMessage());
        }
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<String> sendWhatsAppMessage(
            @RequestParam String phoneNumber,
            @RequestParam String message) {
        try {
            whatsAppService.sendWhatsAppMessage(phoneNumber, message);
            return ResponseEntity.ok("Mensaje de WhatsApp enviado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al enviar mensaje de WhatsApp: " + e.getMessage());
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<String> sendPaymentNotification(
            @RequestParam String phoneNumber,
            @RequestParam String amount,
            @RequestParam String status) {
        try {
            whatsAppService.sendPaymentNotification(phoneNumber, amount, status);
            return ResponseEntity.ok("Notificación de pago enviada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al enviar notificación de pago: " + e.getMessage());
        }
    }
} 