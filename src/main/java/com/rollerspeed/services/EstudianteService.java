package com.rollerspeed.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rollerspeed.models.Estudiante;
import com.rollerspeed.repository.EstudianteRepository;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Obtener todos los estudiantes
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    // Guardar un estudiante
    public Estudiante saveEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Buscar un estudiante por ID
    public Estudiante findById(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    } // ✅ Se corrigió el punto y coma adicional

    // Método adicional para buscar un estudiante por ID y lanzar una excepción si no se encuentra
    public Estudiante findByIdOrThrow(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }


    // Corregimos el nombre del método
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }
    
}
