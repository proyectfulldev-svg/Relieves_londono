package com.relieveslondono.gestion.security;

import com.relieveslondono.gestion.usuario.Rol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private static final String CLAIM_ROL = "rol";
    // HS256 exige una clave de al menos 256 bits (32 bytes).
    private static final int LONGITUD_MINIMA_CLAVE = 32;

    private final SecretKey clave;
    private final Duration expiracion;

    public JwtService(JwtProperties propiedades) {
        String secreto = propiedades.secret();
        if (secreto == null || secreto.getBytes(StandardCharsets.UTF_8).length < LONGITUD_MINIMA_CLAVE) {
            throw new IllegalStateException(
                    "La variable de entorno JWT_SECRET debe tener al menos " + LONGITUD_MINIMA_CLAVE + " caracteres");
        }
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracion = propiedades.expiracion();
    }

    public TokenGenerado generarToken(String email, Rol rol) {
        Instant ahora = Instant.now();
        Instant expiraEn = ahora.plus(expiracion);
        String token = Jwts.builder()
                .subject(email)
                .claim(CLAIM_ROL, rol.name())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(expiraEn))
                .signWith(clave, Jwts.SIG.HS256)
                .compact();
        return new TokenGenerado(token, expiraEn);
    }

    /**
     * Comprueba la firma y la expiración del token y devuelve el email que contiene.
     */
    public String obtenerEmail(String token) {
        return leerClaims(token).getSubject();
    }

    private Claims leerClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(clave)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidoException("El token ha expirado");
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidoException("El token no es válido");
        }
    }
}
