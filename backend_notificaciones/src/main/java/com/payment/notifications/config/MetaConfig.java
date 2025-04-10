package com.payment.notifications.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "meta.whatsapp")
@Data
public class MetaConfig {
    private String accessToken;
    private String phoneNumberId;
} 