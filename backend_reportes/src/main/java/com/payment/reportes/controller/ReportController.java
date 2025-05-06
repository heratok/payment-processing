package com.payment.reportes.controller;

import com.payment.reportes.builder.ReportBuilder;
import com.payment.reportes.model.ReportOptions;
import com.payment.reportes.model.Theme;
import com.payment.reportes.model.Format;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    
    private final ReportBuilder reportBuilder;
    
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportOptions options) {
        byte[] report = reportBuilder
                .withOptions(options)
                .build();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "reporte.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

    @PostMapping(value = "/generate-from-template", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReportFromTemplate(@RequestBody ReportOptions customOptions) {
        // Crear una configuración base (template)
        ReportOptions baseOptions = ReportOptions.builder()
                .includeLogo(true)
                .title("Reporte de Pago")
                .includePaymentDetails(true)
                .includeUserInfo(true)
                .theme(Theme.LIGHT)
                .includeTimestamp(true)
                .footerMessage("© 2024 Sistema de Pagos")
                .format(Format.A4)
                .paymentData(ReportOptions.PaymentData.builder()
                        .amount(0.0)
                        .paymentMethod("")
                        .date("")
                        .transactionId("")
                        .status("")
                        .build())
                .build();

        // Clonar la configuración base
        ReportOptions clonedOptions = baseOptions.clone();

        // Aplicar las personalizaciones y exclusiones
        clonedOptions.setIncludeLogo(customOptions.isIncludeLogo());
        clonedOptions.setIncludePaymentDetails(customOptions.isIncludePaymentDetails());
        clonedOptions.setIncludeUserInfo(customOptions.isIncludeUserInfo());
        clonedOptions.setIncludeTimestamp(customOptions.isIncludeTimestamp());
        
        if (customOptions.getTitle() != null) {
            clonedOptions.setTitle(customOptions.getTitle());
        }
        if (customOptions.getTheme() != null) {
            clonedOptions.setTheme(customOptions.getTheme());
        }
        if (customOptions.getPaymentData() != null) {
            clonedOptions.setPaymentData(customOptions.getPaymentData());
        }
        if (customOptions.getFooterMessage() != null) {
            clonedOptions.setFooterMessage(customOptions.getFooterMessage());
        }

        // Generar el reporte con la configuración clonada y personalizada
        byte[] report = reportBuilder
                .withOptions(clonedOptions)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "reporte_personalizado.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }
}