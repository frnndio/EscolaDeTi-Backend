package com.time02escoladeti.back.foto;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class FotoId extends EntidadeId{

    public FotoId(@NotNull String valor) {
        super(valor);
    }

    public FotoId() {
    }
}
