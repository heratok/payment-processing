package com.payment.reportes.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportOptions implements Cloneable {
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
    
    @Override
    public ReportOptions clone() {
        try {
            ReportOptions cloned = (ReportOptions) super.clone();
            if (this.paymentData != null) {
                cloned.paymentData = this.paymentData.clone();
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar ReportOptions", e);
        }
    }
    
    @Data
    @Builder
    public static class PaymentData implements Cloneable {
        private double amount;
        private String paymentMethod;
        private String date;
        private String transactionId;
        private String status;
        
        @Override
        public PaymentData clone() {
            try {
                return (PaymentData) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Error al clonar PaymentData", e);
            }
        }
    }
} 