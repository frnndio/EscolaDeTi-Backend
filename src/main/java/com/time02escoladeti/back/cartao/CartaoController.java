package com.time02escoladeti.back.cartao;

import com.time02escoladeti.back.Entity.EntidadeIdGerado;
import com.time02escoladeti.back.pessoa.PessoaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import org.springframework.web.util.NestedServletException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cartoes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin("*")
public class CartaoController {
    private final CartaoService service;

    @Autowired
    public CartaoController(CartaoService service) {
        this.service = service;
    }

    @GetMapping(params = {"idPessoa"})
    public ResponseEntity getAllCartoes(@RequestParam("idPessoa") PessoaId idPessoa) {
        return ResponseEntity.ok(service.getAllCartoes(idPessoa));
    }

    @GetMapping("/{id}")
    public ResponseEntity getCartaoById(@PathVariable CartaoId id) {
        return ResponseEntity.ok(service.getCartaoById(id));
    }

    @PostMapping
    public ResponseEntity postCartao(@RequestBody CartaoDto cartao) {
        try {
            CartaoId novoId = service.postCartao(cartao);
            return ResponseEntity.ok(new EntidadeIdGerado(novoId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCartao(@PathVariable CartaoId id) {
        try {
            service.deleteCartao(id);
            return ResponseEntity.status(204).build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Não existe cartão com esse ID!");
        }
    }
}
