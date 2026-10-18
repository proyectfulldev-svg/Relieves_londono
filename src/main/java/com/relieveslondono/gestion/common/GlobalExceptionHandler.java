package com.relieveslondono.gestion.common;

import com.relieveslondono.gestion.security.TokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.de(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Si un campo tiene varios errores, se unen en un solo mensaje.
            errores.merge(error.getField(), error.getDefaultMessage(), (a, b) -> a + "; " + b);
        }
        return ResponseEntity.badRequest()
                .body(ErrorResponse.de(HttpStatus.BAD_REQUEST.value(), "Hay campos inválidos", errores));
    }

    // Token ausente, vencido o inválido en una ruta protegida.
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErrorResponse> manejarTokenInvalido(TokenInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.de(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    // Login fallido. Mensaje genérico a propósito: no revela si el email existe ni si el usuario está inactivo.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> manejarCredencialesInvalidas(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.de(HttpStatus.UNAUTHORIZED.value(), "Credenciales inválidas"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> manejarAccesoDenegado(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.de(HttpStatus.FORBIDDEN.value(), "No tienes permiso para realizar esta acción"));
    }
}
