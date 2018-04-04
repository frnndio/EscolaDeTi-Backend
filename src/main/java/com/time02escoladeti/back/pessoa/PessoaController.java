package com.time02escoladeti.back.pessoa;

import com.time02escoladeti.back.Entity.EntidadeIdGerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pessoas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin("*")
public class PessoaController {

    private final PessoaService service;

    @Autowired
    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity getPessoaById(@PathVariable PessoaId id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/fisica")
    public ResponseEntity adicionarFisica(@RequestBody PessoaFisica novaPessoa) {
        PessoaId idGerado = service.salvar(novaPessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(idGerado));
    }

    @PostMapping("/juridica")
    public ResponseEntity adicionarJuridica(@RequestBody PessoaJuridica novaPessoaJuridica) {
        PessoaId idGerado = service.salvar(novaPessoaJuridica);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(idGerado));
    }

    @PutMapping("/juridica/{id}")
    public ResponseEntity atualizarJuridica(@PathVariable("id") PessoaId idPessoa, @RequestBody PessoaJuridica pessoaJuridica) {
        service.editar(idPessoa, pessoaJuridica);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/fisica/{id}")
    public ResponseEntity atualizarFisica(@PathVariable("id") PessoaId idPessoa, @RequestBody PessoaFisica pessoaFisica) {
        service.editar(idPessoa, pessoaFisica);
        return ResponseEntity.ok().build();
    }

}


