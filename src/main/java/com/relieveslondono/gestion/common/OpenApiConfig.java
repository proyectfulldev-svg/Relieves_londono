package com.relieveslondono.gestion.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Relieves Londoño API",
        version = "1.0",
        description = "API REST del sistema de gestión del taller de mármol funerario Relieves Londoño: "
                + "facturas, proveedores, órdenes de compra, inventario y estadísticas."
))
public class OpenApiConfig {
}
