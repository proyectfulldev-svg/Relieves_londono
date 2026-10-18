package com.relieveslondono.gestion.security;

/**
 * El token falta, está vencido o no es válido. El GlobalExceptionHandler la convierte en 401.
 */
public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException(String mensaje) {
        super(mensaje);
    }
}
