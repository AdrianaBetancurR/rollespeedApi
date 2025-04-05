package com.rollerspeed.controller;

import com.rollerspeed.models.Estudiante;
import com.rollerspeed.repository.EstudianteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Estudiantes", description = "Operaciones relacionadas con la gestión de estudiantes")
@Controller
@RequestMapping("/inscripcion-estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Operation(summary = "Mostrar formulario de inscripción", 
               description = "Muestra el formulario para registrar un nuevo estudiante")
    @ApiResponse(responseCode = "200", description = "Formulario mostrado correctamente")
    @GetMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "registro/inscripcion-estudiantes";
    }

    @Operation(summary = "Registrar estudiante", 
               description = "Registra un nuevo estudiante en el sistema")
    @ApiResponse(responseCode = "200", description = "Estudiante registrado exitosamente")
    @PostMapping
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @ResponseBody
    public ResponseEntity<String> registrarEstudiante(@RequestBody Estudiante estudiante) {
        estudianteRepository.save(estudiante);
        return ResponseEntity.ok("Estudiante registrado exitosamente.");
    }

    @Operation(summary = "Obtener estudiante", 
               description = "Obtiene los detalles de un estudiante por su ID")
    @ApiResponse(responseCode = "200", description = "Estudiante encontrado")
    @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<Estudiante> obtenerEstudiante(
            @Parameter(description = "ID del estudiante", example = "1")
            @PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteRepository.findById(id);
        return estudiante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar estudiantes", 
               description = "Obtiene una lista de todos los estudiantes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida correctamente")
    @ApiResponse(responseCode = "204", description = "No hay estudiantes registrados")
    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<List<Estudiante>> obtenerTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        return estudiantes.isEmpty() ? 
                ResponseEntity.noContent().build() : 
                ResponseEntity.ok(estudiantes);
    }
}