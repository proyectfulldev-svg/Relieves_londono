package com.relieveslondono.gestion.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Valores de jwt.* en application.properties: la clave de firma (de JWT_SECRET) y la duración del token.
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, Duration expiracion) {
}
