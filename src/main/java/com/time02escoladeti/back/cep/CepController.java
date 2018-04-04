package com.time02escoladeti.back.cep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep")
@CrossOrigin("*")
public class CepController {
    private final CepService service;

    @Autowired
    public CepController(CepService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(service.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPorId(@PathVariable CepId id) {
        Cep resultado = service.getPorId(id);
        return ResponseEntity.ok(resultado);
    }

}
