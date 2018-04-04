package com.time02escoladeti.back.cartao;

import com.time02escoladeti.back.Entity.EntidadeId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class CartaoId extends EntidadeId {
    public CartaoId(@NotNull String valor) {
        super(valor);
    }

    public CartaoId() {
    }
}
