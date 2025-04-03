package com.universidad.cesarsoft.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// Configuración de documentación Swagger/OpenAPI
@Configuration
@EnableWebMvc
@OpenAPIDefinition(
        info = @Info(
                title = "Payment Processing API",
                version = "1.0.0",
                description = "API para procesamiento de pagos de la Universidad Popular del Cesar",
                contact = @Contact(
                        name = "Soporte Técnico",
                        email = "soporte@universidad.edu.co"
                )
        ),
        servers = {
                @Server(url = "/api", description = "Servidor de Desarrollo")
        }
)
public class SwaggerConfig {
}