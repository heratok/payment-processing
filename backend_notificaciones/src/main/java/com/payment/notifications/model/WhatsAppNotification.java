package com.payment.notifications.model;

import lombok.Getter;
import java.util.List;

@Getter
public class WhatsAppNotification {
    private final String phoneNumber;
    private final String message;
    private final String mediaUrl;
    private final String caption;
    private final List<String> interactiveButtons;
    private final String language;

    private WhatsAppNotification(Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.message = builder.message;
        this.mediaUrl = builder.mediaUrl;
        this.caption = builder.caption;
        this.interactiveButtons = builder.interactiveButtons;
        this.language = builder.language;
    }

    public static class Builder {
        // Campos obligatorios
        private final String phoneNumber;
        private final String message;

        // Campos opcionales
        private String mediaUrl;
        private String caption;
        private List<String> interactiveButtons;
        private String language;

        public Builder(String phoneNumber, String message) {
            this.phoneNumber = phoneNumber;
            this.message = message;
        }

        public Builder mediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
            return this;
        }

        public Builder caption(String caption) {
            this.caption = caption;
            return this;
        }

        public Builder interactiveButtons(List<String> interactiveButtons) {
            this.interactiveButtons = interactiveButtons;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public WhatsAppNotification build() {
            return new WhatsAppNotification(this);
        }
    }
} 