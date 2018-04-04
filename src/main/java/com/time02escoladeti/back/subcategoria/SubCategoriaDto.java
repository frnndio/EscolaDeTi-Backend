package com.time02escoladeti.back.subcategoria;

import com.time02escoladeti.back.categoria.CategoriaId;

public class SubCategoriaDto {
    private String nome;
    private String descricao;
    private CategoriaId idCategoria;
    private Boolean ativo = Boolean.TRUE;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaId getIdCategoria() {
        return idCategoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
