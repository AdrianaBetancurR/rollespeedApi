package com.rollerspeed.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "instructores")
@Schema(description = "Entidad que representa un instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del instructor", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del instructor", example = "Carlos Méndez")
    private String nombre;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email del instructor", example = "carlos@rollerspeed.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Teléfono del instructor", example = "+1234567890")
    private String telefono;

    @Column(nullable = false)
    @Schema(description = "Especialidad del instructor", example = "Patinaje artístico")
    private String especialidad;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Clases que imparte el instructor")
    private List<Clase> clases;
}