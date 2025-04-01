package com.rollerspeed.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "estudiantes")
@Schema(description = "Entidad que representa un estudiante")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del estudiante", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del estudiante", example = "María González")
    private String nombre;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email del estudiante", example = "maria@example.com")
    private String email;
    
    @Column(nullable = false)
    @Schema(description = "Teléfono del estudiante", example = "+1234567890")
    private String telefono;

    @Column(nullable = false)
    @Schema(description = "Género del estudiante", example = "Femenino")
    private String genero;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Schema(description = "Fecha de nacimiento del estudiante", example = "2000-05-15")
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(description = "Pagos realizados por el estudiante")
    private List<Pago> pagos;
    
    @ManyToMany(mappedBy = "estudiantes")
    @JsonIgnore
    @Schema(description = "Clases en las que está inscrito el estudiante")
    private List<Clase> clases;
}