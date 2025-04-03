package com.universidad.cesarsoft.controller;

import com.universidad.cesarsoft.dto.PaymentRequestDTO;
import com.universidad.cesarsoft.dto.PaymentResponseDTO;
import com.universidad.cesarsoft.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Controlador REST para manejar solicitudes de pago
@RestController
@RequestMapping("/payments")
@Slf4j
@Tag(name = "Payment Processing", description = "API para procesamiento de pagos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private final List<PaymentResponseDTO> paymentHistory = new ArrayList<>();

    // Endpoint para procesar pagos
    @PostMapping("/process")
    @Operation(summary = "Procesar un pago", description = "Procesa un pago según el método especificado")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @Valid @RequestBody PaymentRequestDTO request
    ) {
        log.info("Procesando pago de ${} por método {}",
                request.getAmount(), request.getPaymentMethod());

        boolean paymentProcessed = paymentService.processPayment(
                request.getAmount(),
                request.getPaymentMethod()
        );

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setSuccess(paymentProcessed);
        response.setTransactionId(UUID.randomUUID().toString());
        response.setMessage(paymentProcessed
                ? "Pago procesado exitosamente"
                : "Error procesando el pago"
        );
        response.setPaymentMethod(request.getPaymentMethod());

        // Guardar en el historial
        paymentHistory.add(response);

        log.info("Resultado del pago: {}", response);

        return ResponseEntity.ok(response);
    }

    // Endpoint para obtener el historial de pagos
    @GetMapping("/history")
    @Operation(summary = "Obtener historial de pagos", description = "Retorna el historial de todos los pagos procesados")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentHistory() {
        return ResponseEntity.ok(paymentHistory);
    }

    // Endpoint para obtener detalles de un pago específico
    @GetMapping("/{transactionId}")
    @Operation(summary = "Obtener detalles de un pago", description = "Retorna los detalles de un pago específico")
    public ResponseEntity<PaymentResponseDTO> getPaymentDetails(
            @PathVariable String transactionId
    ) {
        return paymentHistory.stream()
                .filter(payment -> payment.getTransactionId().equals(transactionId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}