package com.payment.notifications.model;

import lombok.Getter;
import java.util.List;

@Getter
public class EmailNotification {
    private final String to;
    private final String subject;
    private final String body;
    private final List<String> cc;
    private final List<String> bcc;
    private final List<String> attachments;
    private final String priority;

    private EmailNotification(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.body = builder.body;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.attachments = builder.attachments;
        this.priority = builder.priority;
    }

    public static class Builder {
        // Campos obligatorios
        private final String to;
        private final String subject;
        private final String body;

        // Campos opcionales
        private List<String> cc;
        private List<String> bcc;
        private List<String> attachments;
        private String priority;

        public Builder(String to, String subject, String body) {
            this.to = to;
            this.subject = subject;
            this.body = body;
        }

        public Builder cc(List<String> cc) {
            this.cc = cc;
            return this;
        }

        public Builder bcc(List<String> bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder attachments(List<String> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public EmailNotification build() {
            return new EmailNotification(this);
        }
    }
} 