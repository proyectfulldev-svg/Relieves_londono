package com.relieveslondono.gestion.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Al arrancar la app, crea el administrador inicial si la tabla usuarios está vacía.
 * El email y la contraseña vienen de las variables de entorno ADMIN_EMAIL y ADMIN_PASSWORD.
 */
@Component
@RequiredArgsConstructor
public class InicializadorAdmin implements ApplicationRunner {

    private final UsuarioService usuarioService;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        usuarioService.crearAdminInicialSiNoHayUsuarios(adminEmail, adminPassword);
    }
}
