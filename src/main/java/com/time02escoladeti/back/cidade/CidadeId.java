package com.time02escoladeti.back.cidade;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class CidadeId extends EntidadeId {
    public CidadeId(@NotNull String valor) {
        super(valor);
    }

    public CidadeId() {

    }
}
