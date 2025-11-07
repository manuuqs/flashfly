package com.flashfly_backend.flashfly.controller;

import com.flashfly_backend.flashfly.model.Destino;
import com.flashfly_backend.flashfly.repository.DestinoRepository;
import com.flashfly_backend.flashfly.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@CrossOrigin(origins = "http://localhost:3000") //PARA EL FRONTEND
public class DestinoController {

    private final DestinoRepository destinoRepository;

    public DestinoController(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    @Autowired
    private DestinoService destinoService;

    //@GetMapping
   // public List<Destino> getAllDestinos() {
     //   return destinoRepository.findAll();
    //}


}
