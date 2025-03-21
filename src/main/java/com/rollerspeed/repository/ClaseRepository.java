package com.rollerspeed.repository;

import java.util.List;

import com.rollerspeed.models.Clase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    // Método para buscar todas las clases
    List<Clase> findAll();

    // Método para buscar una clase por ID
    Optional<Clase> findById(Long id);

    @Query("SELECT c FROM Clase c LEFT JOIN FETCH c.estudiantes WHERE c.id = :id")
Optional<Clase> findByIdWithEstudiantes(@Param("id") Long id);

}
