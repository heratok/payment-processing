package com.payment.notifications.config;

import com.twilio.Twilio;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Configuration
@Component
public class TwilioConfig {

    private final Dotenv dotenv;

    @Autowired
    public TwilioConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @PostConstruct
    public void init() {
        String accountSid = dotenv.get("TWILIO_ACCOUNT_SID");
        String authToken = dotenv.get("TWILIO_AUTH_TOKEN");
        Twilio.init(accountSid, authToken);
    }

    public String getAccountSid() {
        return dotenv.get("TWILIO_ACCOUNT_SID");
    }

    public String getAuthToken() {
        return dotenv.get("TWILIO_AUTH_TOKEN");
    }

    public String getWhatsappFrom() {
        return dotenv.get("TWILIO_WHATSAPP_NUMBER");
    }
} 