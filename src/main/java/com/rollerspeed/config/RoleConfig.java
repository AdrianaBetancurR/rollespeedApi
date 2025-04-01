package com.rollerspeed.config;

import com.rollerspeed.models.Role;
import com.rollerspeed.repository.RoleRepository;
import com.rollerspeed.services.UsuarioService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rollerspeed.models.Usuario;

@Configuration
public class RoleConfig {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository, UsuarioService usuarioService) {
        return args -> {
            crearRolSiNoExiste(roleRepository, "ROLE_ADMIN");
            crearRolSiNoExiste(roleRepository, "ROLE_INSTRUCTOR");
            crearRolSiNoExiste(roleRepository, "ROLE_ESTUDIANTE");
            
            crearUsuarioAdminInicial(usuarioService);
        };
    }
    
    private void crearRolSiNoExiste(RoleRepository roleRepository, String nombreRol) {
        if (!roleRepository.existsByName(nombreRol)) {
            Role rol = new Role();
            rol.setName(nombreRol);
            roleRepository.save(rol);
            System.out.println("Rol creado: " + nombreRol);
        }
    }

    private void crearUsuarioAdminInicial(UsuarioService usuarioService) {
        String adminEmail = "admin@rollerspeed.com";
        if (!usuarioService.existePorEmail(adminEmail)) {
            Usuario admin = usuarioService.registrarUsuario(
                adminEmail,
                "Admin123!", // Cambiar en producción
                "ADMIN"
            );
            System.out.println("Usuario administrador creado: " + admin.getEmail());
        }
    }
}