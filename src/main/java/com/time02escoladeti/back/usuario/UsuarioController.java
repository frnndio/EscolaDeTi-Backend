package com.time02escoladeti.back.usuario;

import com.time02escoladeti.back.Entity.EntidadeIdGerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin("*")
public class UsuarioController {
    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity addUsuario(@RequestBody UsuarioDto usuarioDto) {
        UsuarioId idGerado = service.addUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(idGerado));
    }
}