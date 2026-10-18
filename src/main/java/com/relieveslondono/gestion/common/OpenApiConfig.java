package com.relieveslondono.gestion.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Relieves Londoño API",
                version = "1.0",
                description = "API REST del sistema de gestión del taller de mármol funerario Relieves Londoño: "
                        + "facturas, proveedores, órdenes de compra, inventario y estadísticas."
        ),
        // Todos los endpoints piden el token salvo los marcados con @SecurityRequirements vacío (login).
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Pega aquí el token obtenido en POST /api/auth/login (sin escribir 'Bearer')."
)
public class OpenApiConfig {
}
