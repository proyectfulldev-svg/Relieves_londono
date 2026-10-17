package com.relieveslondono.gestion.proveedor.dto;

import com.relieveslondono.gestion.proveedor.Proveedor;

import java.time.LocalDateTime;

public record ProveedorResponse(
        Long id,
        String nombre,
        String producto,
        String telefono,
        String email,
        String direccion,
        String notas,
        Boolean activo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ProveedorResponse desdeEntidad(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getProducto(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getDireccion(),
                proveedor.getNotas(),
                proveedor.getActivo(),
                proveedor.getCreatedAt(),
                proveedor.getUpdatedAt()
        );
    }
}
