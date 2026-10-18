package com.relieveslondono.gestion.proveedor;

import com.relieveslondono.gestion.proveedor.dto.CambioEstadoRequest;
import com.relieveslondono.gestion.proveedor.dto.ProveedorRequest;
import com.relieveslondono.gestion.proveedor.dto.ProveedorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Gestión de los proveedores de materiales del taller")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @Operation(summary = "Listar proveedores",
            description = "Devuelve los proveedores paginados. Si se envía 'nombre', filtra los que lo contienen "
                    + "(sin distinguir mayúsculas). Por defecto: 20 por página, ordenados por nombre.")
    @GetMapping
    public PagedModel<ProveedorResponse> listarProveedores(
            @RequestParam(required = false) String nombre,
            @ParameterObject @PageableDefault(size = 20, sort = "nombre") Pageable pageable) {
        return new PagedModel<>(proveedorService.listarProveedores(nombre, pageable));
    }

    @Operation(summary = "Obtener un proveedor por id",
            description = "Devuelve 404 si no existe un proveedor con ese id.")
    @GetMapping("/{id}")
    public ProveedorResponse obtenerProveedor(@PathVariable Long id) {
        return proveedorService.obtenerProveedor(id);
    }

    @Operation(summary = "Crear un proveedor",
            description = "Nombre, producto y teléfono son obligatorios; el email debe tener formato válido si se envía. "
                    + "Devuelve 201 con la cabecera Location, o 400 con la lista de campos inválidos.")
    @PostMapping
    public ResponseEntity<ProveedorResponse> crearProveedor(@Valid @RequestBody ProveedorRequest request) {
        ProveedorResponse creado = proveedorService.crearProveedor(request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creado);
    }

    @Operation(summary = "Actualizar un proveedor",
            description = "Reemplaza todos los datos del proveedor: los campos opcionales que no se envíen quedan vacíos. "
                    + "Devuelve 404 si no existe.")
    @PutMapping("/{id}")
    public ProveedorResponse actualizarProveedor(@PathVariable Long id,
                                                 @Valid @RequestBody ProveedorRequest request) {
        return proveedorService.actualizarProveedor(id, request);
    }

    @Operation(summary = "Activar o desactivar un proveedor",
            description = "Cambia el estado con {\"activo\": true|false}. Es un borrado lógico: el proveedor no se elimina.")
    @PatchMapping("/{id}/estado")
    public ProveedorResponse cambiarEstado(@PathVariable Long id,
                                           @Valid @RequestBody CambioEstadoRequest request) {
        return proveedorService.cambiarEstado(id, request.activo());
    }
}
