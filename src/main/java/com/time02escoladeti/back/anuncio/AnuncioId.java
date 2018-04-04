package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class AnuncioId extends EntidadeId {
    public AnuncioId(@NotNull String valor) {
        super(valor);
    }

    public AnuncioId() {
    }
}


