package com.rollerspeed.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rollerspeed.models.Estudiante;
import com.rollerspeed.repository.EstudianteRepository;

@Controller
@RequestMapping("/inscripcion-estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Mostrar el formulario de inscripción
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "registro/inscripcion-estudiantes";
    }

    // Registrar un estudiante (API REST)
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> registrarEstudiante(@RequestBody Estudiante estudiante) {
        estudianteRepository.save(estudiante); // Guarda el estudiante en la base de datos
        return ResponseEntity.ok("Estudiante registrado exitosamente.");
    }

    // Obtener un estudiante por ID (API REST)
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        if (estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todos los estudiantes (API REST)
    @GetMapping("/todos")
    @ResponseBody
    public ResponseEntity<List<Estudiante>> obtenerTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay estudiantes
        }
        return ResponseEntity.ok(estudiantes);
    }

    // Obtener todos los estudiantes (para la vista)
    @GetMapping("/lista")
    public String listarEstudiantes(Model model) {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        model.addAttribute("estudiantes", estudiantes);
        return "lista-estudiantes"; // Nombre de la vista Thymeleaf
    }
}