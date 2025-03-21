package com.rollerspeed.services;

import com.rollerspeed.models.Pago;
import com.rollerspeed.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    public Pago savePago(Pago pago) {
        return pagoRepository.save(pago);
    }
}