    package com.rollerspeed.models;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.Data;
    import java.time.LocalDate;
    import java.util.List;

    @Data
    @Entity
    @Table(name = "estudiantes")
    public class Estudiante {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nombre;

        @Column(nullable = true)
        private String email;
        
        @Column(nullable = false)
        private String telefono;

        @Column(nullable = false)
        private String genero;

        @Column(name = "fecha_nacimiento", nullable = false)
        private LocalDate fechaNacimiento;

        @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
        @JsonIgnore
        private List<Pago> pagos;
        
        @ManyToMany(mappedBy = "estudiantes")
        @JsonIgnore
        private List<Clase> clases;

    // Método para obtener la lista de clases
    public List<Clase> getClases() {
        return clases;
            
        
        }
    }