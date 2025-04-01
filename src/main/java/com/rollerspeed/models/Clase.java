package com.rollerspeed.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "clases")
@Schema(description = "Entidad que representa una clase de patinaje")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la clase", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la clase", example = "Patinaje básico para adultos")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Nivel de la clase", example = "Básico", allowableValues = {"Básico", "Intermedio", "Avanzado"})
    private String nivel;

    @Column(name = "fecha_inicio", nullable = false)
    @Schema(description = "Fecha de inicio de la clase", example = "2023-01-15")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @Schema(description = "Fecha de fin de la clase", example = "2023-03-15")
    private LocalDate fechaFin;

    @Column(nullable = false)
    @Schema(description = "Aula donde se imparte la clase", example = "Aula 1")
    private String aula;

    @Column(nullable = false)
    @Schema(description = "Horario de la clase", example = "Lunes y Miércoles 18:00-20:00")
    private String horario;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    @Schema(description = "Instructor que imparte la clase")
    private Instructor instructor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "clase_estudiante",
        joinColumns = @JoinColumn(name = "clase_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id", referencedColumnName = "id")
    )
    @JsonIgnore
    @Schema(description = "Estudiantes inscritos en la clase")
    private Set<Estudiante> estudiantes = new HashSet<>();

    public void addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
    }

    public List<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes);
    }
}