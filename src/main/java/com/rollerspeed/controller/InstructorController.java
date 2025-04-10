package com.rollerspeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rollerspeed.models.Instructor;
import com.rollerspeed.repository.InstructorRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Instructores", description = "Operaciones relacionadas con la gestión de instructores")
@Controller
@RequestMapping("/inscripcion-instructores")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @Operation(summary = "Mostrar formulario de registro", 
               description = "Muestra el formulario para registrar un nuevo instructor")
    @ApiResponse(responseCode = "200", description = "Formulario mostrado correctamente")
    @GetMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "registro/inscripcion-instructores";
    }

    @Operation(summary = "Registrar instructor", 
               description = "Registra un nuevo instructor en el sistema")
    @ApiResponse(responseCode = "200", description = "Instructor registrado exitosamente")
    @PostMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<String> registrarInstructor(@RequestBody Instructor instructor) {
        instructorRepository.save(instructor);
        return ResponseEntity.ok("Instructor registrado exitosamente.");
    }
}