package com.universidad.cesarsoft.controller;

import com.universidad.cesarsoft.dto.PaymentRequestDTO;
import com.universidad.cesarsoft.dto.PaymentResponseDTO;
import com.universidad.cesarsoft.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// Controlador REST para manejar solicitudes de pago
@RestController
@RequestMapping("/payments")
@Slf4j
@Tag(name = "Payment Processing", description = "API para procesamiento de pagos")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

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

        log.info("Resultado del pago: {}", response);

        return ResponseEntity.ok(response);
    }
}