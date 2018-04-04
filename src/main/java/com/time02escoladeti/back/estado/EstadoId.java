package com.time02escoladeti.back.estado;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class EstadoId extends EntidadeId {
    public EstadoId(@NotNull String valor) {
        super(valor);
    }

    public EstadoId() {

    }
}
