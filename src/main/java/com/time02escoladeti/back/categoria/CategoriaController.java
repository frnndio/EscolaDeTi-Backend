package com.time02escoladeti.back.categoria;

import com.time02escoladeti.back.Entity.EntidadeIdGerado;
import com.time02escoladeti.back.foto.FotoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin("*")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> getAll() {
        return ResponseEntity.ok(categoriaService.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPorId(@PathVariable CategoriaId id) {
        Categoria resultado = categoriaService.getPorId(id);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody CategoriaDto requisicao) {
        CategoriaId idGerado = categoriaService.salvar(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(idGerado));
    }

    @PostMapping("/{id}")
    public ResponseEntity cadastrarFoto(@PathVariable("id") CategoriaId idCategoria,
                                        @RequestParam("foto") MultipartFile foto) throws IOException {
        FotoId idFotoGerado = categoriaService.cadastrarFoto(idCategoria, foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(idFotoGerado));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable CategoriaId id, @RequestBody CategoriaDto requisicao) {
        categoriaService.atualizar(id, requisicao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable CategoriaId id) {
        categoriaService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
