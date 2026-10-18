package com.relieveslondono.gestion.auth;

import com.relieveslondono.gestion.auth.dto.LoginRequest;
import com.relieveslondono.gestion.auth.dto.LoginResponse;
import com.relieveslondono.gestion.security.JwtService;
import com.relieveslondono.gestion.security.TokenGenerado;
import com.relieveslondono.gestion.usuario.UsuarioService;
import com.relieveslondono.gestion.usuario.dto.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String TIPO_TOKEN = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    /**
     * Comprueba email y contraseña (y que el usuario esté activo) y genera el token.
     * Si algo falla, Spring Security lanza una AuthenticationException que se responde como 401.
     */
    public LoginResponse iniciarSesion(LoginRequest request) {
        String email = UsuarioService.normalizarEmail(request.email());
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(email, request.password()));

        UsuarioResponse usuario = usuarioService.obtenerPorEmail(email);
        TokenGenerado token = jwtService.generarToken(usuario.email(), usuario.rol());
        return new LoginResponse(token.token(), TIPO_TOKEN, token.expiraEn(), usuario);
    }

    public UsuarioResponse obtenerUsuarioActual(String email) {
        return usuarioService.obtenerPorEmail(email);
    }
}
