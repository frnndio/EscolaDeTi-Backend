package com.time02escoladeti.back.subcategoria;

import com.time02escoladeti.back.Entity.EntidadeIdGerado;
import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.categoria.CategoriaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subcategorias", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin("*")
public class SubCategoriaController {

    private final SubCategoriaService service;

    @Autowired
    public SubCategoriaController(SubCategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubCategoria>> getAll() {
        return ResponseEntity.ok(service.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPorId(@PathVariable SubCategoriaId id) {
        try {
            return ResponseEntity.ok(service.getPorId(id));
        } catch (ServiceException ex) {
            return ResponseEntity.status(ex.getCodigoStatus()).body(ex.getMessage());
        }
    }

	@GetMapping("/categoria/{id}")
	public ResponseEntity getTodosPorCategoria(@PathVariable("id") CategoriaId idCategoria) {
		List<SubCategoria> resultado = service.getTodosPorCategoria(idCategoria);
		return ResponseEntity.ok(resultado);
	}

    @PostMapping(produces = "application/json")
    public ResponseEntity salvar(@RequestBody SubCategoriaDto requisicao) {
        SubCategoriaId idGerado = service.cadastrarNovo(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntidadeIdGerado(idGerado));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable SubCategoriaId id, @RequestBody SubCategoriaDto requisicao) {
        service.atualizar(id, requisicao);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable SubCategoriaId id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
