package com.relieveslondono.gestion.usuario;

import com.relieveslondono.gestion.common.RecursoNoEncontradoException;
import com.relieveslondono.gestion.usuario.dto.UsuarioResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(normalizarEmail(email))
                .map(UsuarioResponse::desdeEntidad)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con email " + email));
    }

    @Transactional
    public void crearAdminInicialSiNoHayUsuarios(String email, String password) {
        if (usuarioRepository.count() > 0) {
            return;
        }
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            log.warn("La tabla usuarios está vacía y no se definieron ADMIN_EMAIL y ADMIN_PASSWORD: "
                    + "no se creó el administrador inicial y nadie podrá iniciar sesión.");
            return;
        }
        Usuario admin = new Usuario();
        admin.setEmail(normalizarEmail(email));
        admin.setPasswordHash(passwordEncoder.encode(password));
        admin.setNombre("Administrador");
        admin.setRol(Rol.ADMIN);
        admin.setActivo(true);
        usuarioRepository.save(admin);
        log.info("Administrador inicial creado con email {}", admin.getEmail());
    }

    // Los emails se guardan y se buscan siempre en minúsculas y sin espacios.
    public static String normalizarEmail(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }
}
