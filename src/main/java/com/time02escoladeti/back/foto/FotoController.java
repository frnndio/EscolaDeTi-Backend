package com.time02escoladeti.back.foto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fotos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin("*")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @GetMapping("/{id}")
    public ResponseEntity getFoto(@PathVariable("id") FotoId id){
        return ResponseEntity.ok(fotoService.get(id));
    }

}
