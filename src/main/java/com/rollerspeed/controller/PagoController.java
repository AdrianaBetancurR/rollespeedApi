package com.rollerspeed.controller;

import com.rollerspeed.models.Pago;
import com.rollerspeed.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> getAllPagos() {
        return pagoService.getAllPagos();
    }

    @PostMapping
    public Pago createPago(@RequestBody Pago pago) {
        return pagoService.savePago(pago);
    }
}