package com.relieveslondono.gestion.auth.dto;

import com.relieveslondono.gestion.usuario.dto.UsuarioResponse;

import java.time.Instant;

public record LoginResponse(
        String token,
        String tipo,
        Instant expiraEn,
        UsuarioResponse usuario
) {
}
