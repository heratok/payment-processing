package com.payment.reportes.builder;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.payment.reportes.model.ReportOptions;
import com.payment.reportes.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ReportBuilder {
    
    private Document document;
    private ReportOptions options;
    
    public ReportBuilder withOptions(ReportOptions options) {
        this.options = options;
        return this;
    }
    
    public byte[] build() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        this.document = new Document(pdf);
        
        buildTitle();
        buildLogo();
        buildPaymentDetails();
        buildUserInfo();
        buildTimestamp();
        buildFooter();
        
        document.close();
        return outputStream.toByteArray();
    }
    
    private void buildTitle() {
        if (options.getTitle() != null) {
            document.add(new Paragraph(options.getTitle())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(20));
        }
    }
    
    private void buildLogo() {
        if (options.isIncludeLogo()) {
            // Aquí se agregaría la lógica para incluir el logo
            // document.add(new Image(ImageDataFactory.create(logoPath)));
        }
    }
    
    private void buildPaymentDetails() {
        if (options.isIncludePaymentDetails() && options.getPaymentData() != null) {
            Table paymentTable = new Table(2);
            paymentTable.addCell("Número de Transacción");
            paymentTable.addCell(options.getPaymentData().getTransactionId());
            paymentTable.addCell("Método de Pago");
            paymentTable.addCell(options.getPaymentData().getPaymentMethod());
            paymentTable.addCell("Monto");
            paymentTable.addCell(String.format("$%.2f", options.getPaymentData().getAmount()));
            paymentTable.addCell("Estado");
            paymentTable.addCell(options.getPaymentData().getStatus());
            document.add(paymentTable);
        }
    }
    
    private void buildUserInfo() {
        if (options.isIncludeUserInfo() && options.getPaymentData() != null) {
            Table userTable = new Table(2);
            userTable.addCell("Fecha de Transacción");
            userTable.addCell(options.getPaymentData().getDate());
            document.add(userTable);
        }
    }
    
    private void buildTimestamp() {
        if (options.isIncludeTimestamp()) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            document.add(new Paragraph("Fecha y hora de generación: " + timestamp)
                    .setTextAlignment(TextAlignment.RIGHT));
        }
    }
    
    private void buildFooter() {
        if (options.getFooterMessage() != null) {
            document.add(new Paragraph(options.getFooterMessage())
                    .setTextAlignment(TextAlignment.CENTER));
        }
    }
} 