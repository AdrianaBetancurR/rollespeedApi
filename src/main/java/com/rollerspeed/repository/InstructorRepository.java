package com.rollerspeed.repository;

import com.rollerspeed.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findById(Long id);  // Busca por ID
    Optional<Instructor> findByEmail(String email);  // Busca por correo
}
