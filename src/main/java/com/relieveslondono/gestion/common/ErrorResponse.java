package com.relieveslondono.gestion.common;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String mensaje,
        Map<String, String> errores
) {

    public static ErrorResponse de(int status, String mensaje) {
        return new ErrorResponse(LocalDateTime.now(), status, mensaje, Map.of());
    }

    public static ErrorResponse de(int status, String mensaje, Map<String, String> errores) {
        return new ErrorResponse(LocalDateTime.now(), status, mensaje, errores);
    }
}
