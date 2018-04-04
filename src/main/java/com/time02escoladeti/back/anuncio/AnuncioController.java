package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.usuario.UsuarioId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
@CrossOrigin("*")
public class AnuncioController {
    private final AnuncioService service;

    @Autowired
    public AnuncioController(AnuncioService service) {
        this.service = service;
    }

    @GetMapping(value = "/agricola", params = {"limit", "page"})
    public Page<AnuncioMaquinario> findAnunciosAgricolaPaginado(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                                @RequestParam(value = "page", defaultValue = "1") Integer page) {
        Page<AnuncioMaquinario> anuncios = service.find(limit, page - 1);
        return anuncios;
    }

    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<Anuncio> resultado = service.findAll();
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @GetMapping("getAnuncioImpulsionados/{id}")
    public ResponseEntity getAnuncioImpulsionados(@PathVariable UsuarioId id) {
        try {
            List<Anuncio> resultado = service.getAnuncioImpulsionados(id);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @GetMapping("findAllAnunciosPublicados/{id}")
    public ResponseEntity findAllAnunciosPublicados(@PathVariable UsuarioId id) {
        try {
            List<Anuncio> resultado = service.findAllAnunciosPublicados(id);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @GetMapping("findAllAnunciosAguardandoAprovacao/{id}")
    public ResponseEntity findAllAnunciosAguardandoAprovacao(@PathVariable UsuarioId id) {
        try {
            List<Anuncio> resultado = service.findAllAnunciosAguardandoAprovacao(id);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @GetMapping("findAllAnunciosExpirados/{id}")
    public ResponseEntity findAllAnunciosExpirados(@PathVariable UsuarioId id) {
        try {
            List<Anuncio> resultado = service.findAllAnunciosExpirados(id);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @GetMapping("findAnuncioById/{id}")
    public ResponseEntity findAnuncioById(@PathVariable AnuncioId id) {
        try {
            Anuncio resultado = service.findAnuncioById(id);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @DeleteMapping("/{id)")
    public ResponseEntity excluirAnuncio(@PathVariable AnuncioId id) {
        try {
            AnuncioId resultado = service.excluirAnuncio(id);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @PostMapping("/agricola/cadastrar")
    public ResponseEntity cadastrarAnuncio(@RequestBody AnuncioMaquinarioDto novoAnuncio) {
        try {
            AnuncioId resultado = service.createAnuncio(novoAnuncio);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAnuncio(@PathVariable AnuncioId idAnuncio, @RequestBody AnuncioMaquinarioDto anuncio) {
        try {
            AnuncioId resultado = service.updateAnuncio(idAnuncio, anuncio);
            return ResponseEntity.ok(resultado);
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }
}
