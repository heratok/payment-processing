package com.payment.reportes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportOptions {
    private boolean includeLogo;
    private String title;
    private boolean includePaymentDetails;
    private boolean includeUserInfo;
    private Theme theme;
    private boolean includeTimestamp;
    private String footerMessage;
    private Format format;
    
    // Datos del pago
    private PaymentData paymentData;
    
    @Data
    @Builder
    public static class PaymentData {
        private double amount;
        private String paymentMethod;
        private String date;
        private String transactionId;
        private String status;
    }
} 