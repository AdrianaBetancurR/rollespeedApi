package com.rollerspeed.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String nivel; // Básico, Intermedio, Avanzado

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String aula;

    @Column(nullable = false)
    private String horario;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
    name = "clase_estudiante",
    joinColumns = @JoinColumn(name = "clase_id"),
    inverseJoinColumns = @JoinColumn(name = "estudiante_id", referencedColumnName = "id")
)
@JsonIgnore
private Set<Estudiante> estudiantes = new HashSet<>();
    

    // Método para agregar un estudiante a la clase
    public void addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
    }


    public List<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes);
    }
    
}
