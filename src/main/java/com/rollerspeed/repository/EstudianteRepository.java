package com.rollerspeed.repository;

import com.rollerspeed.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    @Query("SELECT e FROM Estudiante e WHERE e.id = :estudianteId")
    Optional<Estudiante> findById(@Param("estudianteId") Long estudianteId);
}