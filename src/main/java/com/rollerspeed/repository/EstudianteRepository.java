package com.rollerspeed.repository;

import com.rollerspeed.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findById(Long id);  // Busca por ID
    Optional<Estudiante> findByEmail(String email);  // Busca por correo
}
