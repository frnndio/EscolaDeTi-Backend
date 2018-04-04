package com.time02escoladeti.back.foto;

import com.time02escoladeti.back.Entity.Entidade;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Foto extends Entidade<FotoId> {
    @Column
    private String nomeFoto;

    private Foto() {
        super(new FotoId());
    }

    public Foto(String nomeFoto) {
        this();
        this.nomeFoto = nomeFoto;
    }

    public String getNomeFoto() {
        return this.nomeFoto;
    }
}
