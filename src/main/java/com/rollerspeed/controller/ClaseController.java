package com.rollerspeed.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rollerspeed.models.Clase;
import com.rollerspeed.models.Estudiante;
import com.rollerspeed.models.Instructor;
import com.rollerspeed.services.ClaseService;
import com.rollerspeed.services.EstudianteService;
import com.rollerspeed.services.InstructorService;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private EstudianteService estudianteService;

    // Listar todas las clases
    @GetMapping
    public String listarClases(Model model) {
        List<Clase> clases = claseService.findAll();
        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();

        model.addAttribute("clases", clases);
        model.addAttribute("estudiantes", estudiantes);

        return "clases/listar";
    }

    // Mostrar formulario para crear una nueva clase
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaClase(Model model) {
        List<Instructor> instructores = instructorService.getAllInstructores();
        model.addAttribute("instructores", instructores);
        model.addAttribute("clase", new Clase());
        return "clases/formulario";
    }

    // Guardar una nueva clase
    @PostMapping("/guardar")
    public String guardarClase(@ModelAttribute Clase clase) {
        claseService.save(clase);
        return "redirect:/clases";
    }

    // Ver detalles de una clase específica
    @GetMapping("/{id}")
    public String verClase(@PathVariable Long id, Model model) {
        Clase clase = claseService.findById(id);
        if (clase == null) {
            return "redirect:/clases"; // Redirigir si la clase no existe
        }
        model.addAttribute("clase", clase);
        return "clases/detalles";
    }

    // Mostrar formulario para inscribir un estudiante en una clase
    @GetMapping("/inscribirse/{id}")
    public String mostrarFormularioInscripcion(@PathVariable Long id, Model model) {
        Clase clase = claseService.findById(id);
        if (clase == null) {
            return "redirect:/clases"; // Redirigir si la clase no existe
        }

        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();
        model.addAttribute("clase", clase);
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("estudiante", new Estudiante());

        return "clases/inscripcion";
    }

    // Inscribir un estudiante en una clase (API REST)
    @PostMapping("/inscribir/{id}")
    public ResponseEntity<?> inscribirEstudiante(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Long estudianteId = Long.parseLong(payload.get("estudianteId").toString());

        Clase clase = claseService.findById(id);
        Estudiante estudiante = estudianteService.findById(estudianteId);

        if (clase == null || estudiante == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Clase o estudiante no encontrado"));
        }

        claseService.inscribirEstudiante(id, estudiante);
        return ResponseEntity.ok(Collections.singletonMap("success", "Estudiante inscrito correctamente"));
    }

    // Obtener estudiantes inscritos en una clase (API REST)
    @GetMapping("/estudiantes-inscritos/{id}")
    public ResponseEntity<List<Estudiante>> getEstudiantesInscritos(@PathVariable Long id) {
        Clase clase = claseService.findByIdWithEstudiantes(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));
        return ResponseEntity.ok(clase.getEstudiantes());
    }

    // Mostrar formulario de inscripción de estudiantes
    @GetMapping("/inscripcion-estudiantes")
    public String mostrarFormularioInscripcionEstudiantes(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());
        return "registro/inscripcion-estudiantes";
    }
}