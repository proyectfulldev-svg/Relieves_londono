package com.relieveslondono.gestion.proveedor;

import com.relieveslondono.gestion.common.RecursoNoEncontradoException;
import com.relieveslondono.gestion.proveedor.dto.ProveedorRequest;
import com.relieveslondono.gestion.proveedor.dto.ProveedorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Transactional
    public ProveedorResponse crearProveedor(ProveedorRequest request) {
        Proveedor proveedor = new Proveedor();
        aplicarDatos(proveedor, request);
        return ProveedorResponse.desdeEntidad(proveedorRepository.save(proveedor));
    }

    @Transactional(readOnly = true)
    public Page<ProveedorResponse> listarProveedores(String nombre, Pageable pageable) {
        Page<Proveedor> pagina = (nombre == null || nombre.isBlank())
                ? proveedorRepository.findAll(pageable)
                : proveedorRepository.buscarPorNombre(nombre.trim(), pageable);
        return pagina.map(ProveedorResponse::desdeEntidad);
    }

    @Transactional(readOnly = true)
    public ProveedorResponse obtenerProveedor(Long id) {
        return ProveedorResponse.desdeEntidad(buscarProveedor(id));
    }

    @Transactional
    public ProveedorResponse actualizarProveedor(Long id, ProveedorRequest request) {
        Proveedor proveedor = buscarProveedor(id);
        aplicarDatos(proveedor, request);
        return ProveedorResponse.desdeEntidad(proveedorRepository.saveAndFlush(proveedor));
    }

    @Transactional
    public ProveedorResponse cambiarEstado(Long id, boolean activo) {
        Proveedor proveedor = buscarProveedor(id);
        proveedor.setActivo(activo);
        return ProveedorResponse.desdeEntidad(proveedorRepository.saveAndFlush(proveedor));
    }

    private Proveedor buscarProveedor(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un proveedor con id " + id));
    }

    private void aplicarDatos(Proveedor proveedor, ProveedorRequest request) {
        proveedor.setNombre(request.nombre().trim());
        proveedor.setProducto(request.producto().trim());
        proveedor.setTelefono(request.telefono().trim());
        proveedor.setEmail(limpiarOpcional(request.email()));
        proveedor.setDireccion(limpiarOpcional(request.direccion()));
        proveedor.setNotas(limpiarOpcional(request.notas()));
    }

    // Los campos opcionales vacíos se guardan como NULL, no como texto vacío.
    private String limpiarOpcional(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }
}
