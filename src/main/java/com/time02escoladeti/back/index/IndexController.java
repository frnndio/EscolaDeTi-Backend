package com.time02escoladeti.back.index;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.anuncio.Anuncio;
import com.time02escoladeti.back.anuncio.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
@CrossOrigin("*")
public class IndexController {
    private final AnuncioService service;

    @Autowired
    public IndexController(AnuncioService service) {
        this.service = service;
    }

    @GetMapping("getAnunciosImpulsionados")
    public ResponseEntity getAnunciosImpulsionados() {
        try {
            List<Anuncio> resultado = service.getAllAnuncioImpulsionado();
            return ResponseEntity.ok(resultado);
        }
        catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }
}
