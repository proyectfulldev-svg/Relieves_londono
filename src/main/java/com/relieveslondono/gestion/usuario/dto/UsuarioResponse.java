package com.relieveslondono.gestion.usuario.dto;

import com.relieveslondono.gestion.usuario.Rol;
import com.relieveslondono.gestion.usuario.Usuario;

public record UsuarioResponse(
        Long id,
        String email,
        String nombre,
        Rol rol,
        Boolean activo
) {

    public static UsuarioResponse desdeEntidad(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol(),
                usuario.getActivo()
        );
    }
}
