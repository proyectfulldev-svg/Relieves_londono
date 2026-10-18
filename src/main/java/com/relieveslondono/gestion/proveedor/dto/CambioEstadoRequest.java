package com.relieveslondono.gestion.proveedor.dto;

import jakarta.validation.constraints.NotNull;

public record CambioEstadoRequest(

        @NotNull(message = "El campo activo es obligatorio")
        Boolean activo
) {
}
