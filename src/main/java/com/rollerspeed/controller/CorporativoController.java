package com.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorporativoController {

    @GetMapping("/mision")
    public String mision() {
        return "corporativo/mision";
    }

    @GetMapping("/vision")
    public String vision() {
        return "corporativo/vision";
    }

    @GetMapping("/valores")
    public String valores() {
        return "corporativo/valores";
    }

    @GetMapping("/servicios")
    public String servicios() {
        return "corporativo/servicios";
    }
}