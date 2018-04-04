package com.time02escoladeti.back.endereco;

import com.time02escoladeti.back.Entity.EntidadeIdGerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin("*")
public class EnderecoController {
    private final EnderecoService service;

    @Autowired
    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity getEnderecoById(@PathVariable EnderecoId id) {
        return ResponseEntity.ok(service.getEnderecoById(id));
    }

    @PostMapping
    public ResponseEntity postEndereco(@RequestBody EnderecoDto endereco) {
        EnderecoId enderecoId = service.postEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(enderecoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity putEndereco(@PathVariable EnderecoId id, @RequestBody EnderecoDto endereco) {
        service.putEndereco(id, endereco);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEndereco(@PathVariable EnderecoId id) {
        service.deleteEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
