package com.rollerspeed.controller;

import com.rollerspeed.models.Clase;
import com.rollerspeed.models.Estudiante;
import com.rollerspeed.models.Instructor;
import com.rollerspeed.services.ClaseService;
import com.rollerspeed.services.EstudianteService;
import com.rollerspeed.services.InstructorService;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name = "Clases", description = "Operaciones relacionadas con la gestión de clases")
@Controller
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private EstudianteService estudianteService;

    @Operation(summary = "Listar clases", description = "Obtiene una lista de todas las clases disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de clases obtenida correctamente")
    @GetMapping
    public String listarClases(Model model) {
        List<Clase> clases = claseService.findAll();
        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();

        model.addAttribute("clases", clases);
        model.addAttribute("estudiantes", estudiantes);

        return "clases/listar";
    }

    @Operation(summary = "Mostrar formulario de nueva clase", 
               description = "Muestra el formulario para crear una nueva clase")
    @ApiResponse(responseCode = "200", description = "Formulario mostrado correctamente")
    @GetMapping("/nueva")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public String mostrarFormularioNuevaClase(Model model) {
        List<Instructor> instructores = instructorService.getAllInstructores();
        model.addAttribute("instructores", instructores);
        model.addAttribute("clase", new Clase());
        return "clases/formulario";
    }

    @Operation(summary = "Guardar clase", 
               description = "Guarda una nueva clase en el sistema")
    @ApiResponse(responseCode = "302", description = "Clase creada, redirección a lista de clases")
    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public String guardarClase(@ModelAttribute Clase clase) {
        claseService.save(clase);
        return "redirect:/clases";
    }

    @Operation(summary = "Ver detalles de clase", 
               description = "Muestra los detalles de una clase específica")
    @ApiResponse(responseCode = "200", description = "Detalles de clase obtenidos correctamente")
    @ApiResponse(responseCode = "302", description = "Redirección si la clase no existe")
    @GetMapping("/{id}")
    public String verClase(
            @Parameter(description = "ID de la clase", example = "1")
            @PathVariable Long id, Model model) {
        Clase clase = claseService.findById(id);
        if (clase == null) {
            return "redirect:/clases";
        }
        model.addAttribute("clase", clase);
        return "clases/detalles";
    }

    @Operation(summary = "Inscribir estudiante", 
               description = "Inscribe un estudiante en una clase específica")
    @ApiResponse(responseCode = "200", description = "Estudiante inscrito correctamente")
    @ApiResponse(responseCode = "400", description = "Clase o estudiante no encontrado")
    @PostMapping("/inscribir/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<?> inscribirEstudiante(
            @Parameter(description = "ID de la clase", example = "1")
            @PathVariable Long id, 
            @RequestBody Map<String, Object> payload) {
        Long estudianteId = Long.parseLong(payload.get("estudianteId").toString());

        Clase clase = claseService.findById(id);
        Estudiante estudiante = estudianteService.findById(estudianteId);

        if (clase == null || estudiante == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Clase o estudiante no encontrado"));
        }

        claseService.inscribirEstudiante(id, estudiante);
        return ResponseEntity.ok(Collections.singletonMap("success", "Estudiante inscrito correctamente"));
    }

    @Operation(summary = "Obtener estudiantes inscritos", 
               description = "Obtiene la lista de estudiantes inscritos en una clase")
    @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida correctamente")
    @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    @GetMapping("/estudiantes-inscritos/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<List<Estudiante>> getEstudiantesInscritos(
            @Parameter(description = "ID de la clase", example = "1")
            @PathVariable Long id) {
        Clase clase = claseService.findByIdWithEstudiantes(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));
        return ResponseEntity.ok(clase.getEstudiantes());
    }
}