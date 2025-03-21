package com.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventosController {

    @GetMapping
    public String mostrarEventos(Model model) {
        // Lista de eventos (puedes reemplazar esto con datos de una base de datos)
        List<Evento> eventos = Arrays.asList(
            new Evento("Clase de Patinaje para Principiantes", "2023-11-15", "16:00 - 18:00", "Cancha Principal"),
            new Evento("Taller de Mantenimiento de Patines", "2023-11-20", "10:00 - 12:00", "Taller de Equipos"),
            new Evento("Competencia Interna de Velocidad", "2023-11-25", "09:00 - 14:00", "Pista de Velocidad"),
            new Evento("Charla sobre Nutrición Deportiva", "2023-11-30", "17:00 - 19:00", "Aula 101")
        );

        // Agrega la lista de eventos al modelo
        model.addAttribute("eventos", eventos);
        return "eventos/eventos";
    }

    // Clase interna para representar un evento
    public static class Evento {
        private String nombre;
        private String fecha;
        private String horario;
        private String ubicacion;

        public Evento(String nombre, String fecha, String horario, String ubicacion) {
            this.nombre = nombre;
            this.fecha = fecha;
            this.horario = horario;
            this.ubicacion = ubicacion;
        }

        // Getters
        public String getNombre() {
            return nombre;
        }

        public String getFecha() {
            return fecha;
        }

        public String getHorario() {
            return horario;
        }

        public String getUbicacion() {
            return ubicacion;
        }
    }
}