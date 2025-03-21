package com.rollerspeed.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;


    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    public enum MetodoPago {
        EFECTIVO, TARJETA, TRANSFERENCIA
    }
    
}