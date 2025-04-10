package com.universidad.cesarsoft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

// Configuración para habilitar CORS (Cross-Origin Resource Sharing)
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",  // Frontend local
                        "http://localhost:8080",  // Backend local
                        "http://localhost:5173",  // Vite dev server default
                        "http://localhost:5174",  // Vite dev server alternate
                        "http://127.0.0.1:5173",  // Vite IP access
                        "http://127.0.0.1:5174",  // Vite IP access alternate
                        "http://localhost:4173",  // Vite preview
                        "https://tudominio.com"   // Dominio de producción
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600); // 1 hora de caché preflight
    }
}