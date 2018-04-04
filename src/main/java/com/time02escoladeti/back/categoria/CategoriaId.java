package com.time02escoladeti.back.categoria;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class CategoriaId extends EntidadeId {
    public CategoriaId(@NotNull String valor) {
        super(valor);
    }

    public CategoriaId() {
    }
}
