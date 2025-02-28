package com.rollerspeed.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "inicio"; // Retorna la vista inicio.html
    }

    @GetMapping("/mision")
    public String mision() {
        return "mision"; // Retorna la vista mision.html
    }

    @GetMapping("/vision")
    public String vision() {
        return "vision"; // Retorna la vista vision.html
    }

    @GetMapping("/valores")
    public String valores() {
        return "valores"; // Retorna la vista valores.html
    }

    @GetMapping("/servicios")
    public String servicios() {
        return "servicios"; // Retorna la vista servicios.html
    }

    @GetMapping("/eventos")
    public String eventos() {
        return "eventos"; // Retorna la vista eventos.html
    }
}