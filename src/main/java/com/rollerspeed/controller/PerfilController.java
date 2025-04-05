package com.rollerspeed.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @GetMapping("/perfil")
    public String mostrarPerfil(Authentication authentication, Model model) {
        // Obtener nombre y rol del usuario actual
        String nombreUsuario = authentication.getName();
        String rolUsuario = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .orElse("Sin rol");
        
        model.addAttribute("nombre", nombreUsuario);
        model.addAttribute("rol", rolUsuario);
        return "perfil"; // Nombre de la plantilla Thymeleaf
    }
}
