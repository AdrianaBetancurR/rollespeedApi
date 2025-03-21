    package com.rollerspeed.models;

    import jakarta.persistence.*;
    import lombok.Data;
    import java.util.List;

    @Data
    @Entity
    @Table(name = "instructores")
    public class Instructor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nombre;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String telefono;

        @Column(nullable = false)
        private String especialidad;

        @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<Clase> clases;
    }