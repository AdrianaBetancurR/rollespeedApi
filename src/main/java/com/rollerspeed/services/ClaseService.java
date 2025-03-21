package com.rollerspeed.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rollerspeed.models.Clase;
import com.rollerspeed.models.Estudiante;
import com.rollerspeed.repository.ClaseRepository;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    // Obtener todas las clases
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    // Obtener una clase por ID
    public Clase findById(Long id) {
        return claseRepository.findById(id).orElse(null);
    }

    // Guardar una clase
    public Clase save(Clase clase) {
        return claseRepository.save(clase);
    }

    // Inscribir un estudiante en una clase
    @Transactional
    public void inscribirEstudiante(Long claseId, Estudiante estudiante) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));
        clase.addEstudiante(estudiante);
        claseRepository.save(clase);
    }

    // Obtener una clase con sus estudiantes
    public Optional<Clase> findByIdWithEstudiantes(Long id) {
        return claseRepository.findByIdWithEstudiantes(id);
    }
    
    }
    

