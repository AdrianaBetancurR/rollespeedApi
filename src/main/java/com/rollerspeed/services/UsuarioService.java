package com.rollerspeed.services;

import com.rollerspeed.models.Role;
import com.rollerspeed.models.Usuario;
import com.rollerspeed.repository.RoleRepository;
import com.rollerspeed.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, 
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Transactional
    public Usuario registrarUsuario(String email, String password, String nombreRol) {
        validarDatosRegistro(email, password);
        
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEnabled(true);

        Role role = obtenerRol(nombreRol);
        usuario.setRoles(Collections.singleton(role));
        
        return usuarioRepository.save(usuario);
    }

    private void validarDatosRegistro(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (existePorEmail(email)) {
            throw new IllegalStateException("El email ya está registrado");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
    }

    private Role obtenerRol(String nombreRol) {
        String roleName = "ROLE_" + nombreRol.toUpperCase();
        return roleRepository.findByName(roleName)
            .orElseThrow(() -> new IllegalArgumentException("Rol '" + nombreRol + "' no encontrado"));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }
}