package com.time02escoladeti.back.cidade;

import com.time02escoladeti.back.estado.EstadoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cidades")
@CrossOrigin("*")
public class CidadeController {
    private CidadeService service;

    @Autowired
    public CidadeController(CidadeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Cidade getPorId(@PathVariable CidadeId id) {
        return service.getPorId(id);
    }

    @GetMapping("estado/{id}")
    public ResponseEntity getTodosPorEstado(@PathVariable EstadoId id) {
        return ResponseEntity.ok(service.getTodosPorEstado(id));
    }
}
