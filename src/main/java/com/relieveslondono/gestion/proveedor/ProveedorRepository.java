package com.relieveslondono.gestion.proveedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Proveedor> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);
}
