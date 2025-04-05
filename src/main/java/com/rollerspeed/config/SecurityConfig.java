package com.rollerspeed.config;

import com.rollerspeed.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Acceso público
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/index"),
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/registro"),
                    new AntPathRequestMatcher("/error"),
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**"),
                    new AntPathRequestMatcher("/h2-console/**"),
                    // Rutas para OpenAPI/Swagger
                    new AntPathRequestMatcher("/v3/api-docs/**"),
                    new AntPathRequestMatcher("/swagger-ui/**"),
                    new AntPathRequestMatcher("/swagger-ui.html"),
                    new AntPathRequestMatcher("/swagger-resources/**"),
                    new AntPathRequestMatcher("/webjars/**"),
                    new AntPathRequestMatcher("/eventos"),
                    new AntPathRequestMatcher("/mision"),
                    new AntPathRequestMatcher("/vision"),
                    new AntPathRequestMatcher("/valores"),
                    new AntPathRequestMatcher("/servicios")
                ).permitAll()

                // Rutas protegidas por rol
                .requestMatchers(
                    new AntPathRequestMatcher("/inscripcion-estudiantes"),
                    new AntPathRequestMatcher("/mis-clases")
                
                ).hasRole("ESTUDIANTE")

                .requestMatchers(
                    new AntPathRequestMatcher("/inscripcion-instructores"),
                    new AntPathRequestMatcher("/clases/nueva"),
                    new AntPathRequestMatcher("/clases/guardar"),
                    new AntPathRequestMatcher("/clases/inscritos/**")
                ).hasAnyRole("INSTRUCTOR", "ADMIN")

                .requestMatchers(
                    new AntPathRequestMatcher("/admin/**")
                ).hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/?login=true", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> usuarioRepository.findByEmail(username)
            .map(usuario -> new User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList())
            ))
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
