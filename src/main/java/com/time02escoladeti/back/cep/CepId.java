package com.time02escoladeti.back.cep;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class CepId extends EntidadeId {
    public CepId(@NotNull String valor) {
        super(valor);
    }

    public CepId() {
    }
}