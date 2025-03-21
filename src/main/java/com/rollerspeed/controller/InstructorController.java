package com.rollerspeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rollerspeed.models.Instructor;
import com.rollerspeed.repository.InstructorRepository;

@Controller
@RequestMapping("/inscripcion-instructores")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "registro/inscripcion-instructores";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> registrarInstructor(@RequestBody Instructor instructor) {
        instructorRepository.save(instructor); // Guarda el instructor en la base de datos
        return ResponseEntity.ok("Instructor registrado exitosamente.");
    }
}