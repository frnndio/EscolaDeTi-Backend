package com.time02escoladeti.back.endereco;


import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class EnderecoId extends EntidadeId {
    public EnderecoId(@NotNull String valor) {
        super(valor);
    }

    public EnderecoId() {
    }
}
