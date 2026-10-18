package com.relieveslondono.gestion.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Lee el header "Authorization: Bearer <token>" y, si el token es válido y el usuario está activo,
 * deja al usuario autenticado para esta petición. Si no, la petición sigue sin autenticar y
 * Spring Security responde 401 en las rutas protegidas.
 */
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    /** Atributo de la petición donde se guarda por qué no se aceptó el token, para el mensaje del 401. */
    public static final String ATRIBUTO_ERROR_TOKEN = "errorToken";

    private static final String PREFIJO_BEARER = "Bearer ";

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(PREFIJO_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(PREFIJO_BEARER.length()).trim();
        try {
            String email = jwtService.obtenerEmail(token);
            UserDetails usuario = usuarioDetailsService.loadUserByUsername(email);
            if (usuario.isEnabled()) {
                autenticar(usuario, request);
            } else {
                request.setAttribute(ATRIBUTO_ERROR_TOKEN, "El usuario está inactivo");
            }
        } catch (TokenInvalidoException e) {
            request.setAttribute(ATRIBUTO_ERROR_TOKEN, e.getMessage());
        } catch (UsernameNotFoundException e) {
            request.setAttribute(ATRIBUTO_ERROR_TOKEN, "El usuario del token no existe");
        }

        filterChain.doFilter(request, response);
    }

    private void autenticar(UserDetails usuario, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken autenticacion =
                UsernamePasswordAuthenticationToken.authenticated(usuario, null, usuario.getAuthorities());
        autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext contexto = SecurityContextHolder.createEmptyContext();
        contexto.setAuthentication(autenticacion);
        SecurityContextHolder.setContext(contexto);
    }
}
