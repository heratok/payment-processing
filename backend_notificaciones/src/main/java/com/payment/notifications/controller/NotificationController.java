package com.payment.notifications.controller;

import com.payment.notifications.model.NotificationRequest;
import com.payment.notifications.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            notificationService.sendNotification(request);
            return ResponseEntity.ok("Notificación enviada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al enviar la notificación: " + e.getMessage());
        }
    }
} 