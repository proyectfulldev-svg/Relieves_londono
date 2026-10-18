package com.relieveslondono.gestion.auth;

import com.relieveslondono.gestion.auth.dto.LoginRequest;
import com.relieveslondono.gestion.auth.dto.LoginResponse;
import com.relieveslondono.gestion.usuario.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Inicio de sesión con JWT y datos del usuario autenticado")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Iniciar sesión",
            description = "Recibe email y contraseña y devuelve un token JWT válido por 8 horas. "
                    + "Responde 401 \"Credenciales inválidas\" si los datos no son correctos o el usuario está inactivo.")
    @SecurityRequirements
    @PostMapping("/login")
    public LoginResponse iniciarSesion(@Valid @RequestBody LoginRequest request) {
        return authService.iniciarSesion(request);
    }

    @Operation(summary = "Usuario actual",
            description = "Devuelve los datos del usuario dueño del token enviado en el header Authorization.")
    @GetMapping("/me")
    public UsuarioResponse obtenerUsuarioActual(Authentication autenticacion) {
        return authService.obtenerUsuarioActual(autenticacion.getName());
    }
}
