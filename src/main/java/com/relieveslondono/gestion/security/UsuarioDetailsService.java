package com.relieveslondono.gestion.security;

import com.relieveslondono.gestion.usuario.Usuario;
import com.relieveslondono.gestion.usuario.UsuarioRepository;
import com.relieveslondono.gestion.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Le dice a Spring Security cómo cargar un usuario de la tabla usuarios a partir de su email.
 */
@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.buscarPorEmail(UsuarioService.normalizarEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.withUsername(usuario.getEmail())
                .password(usuario.getPasswordHash())
                .roles(usuario.getRol().name())
                .disabled(!usuario.getActivo())
                .build();
    }
}
