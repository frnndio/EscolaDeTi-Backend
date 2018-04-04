package com.time02escoladeti.back.categoria;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoriaDto {
    @NotNull
    @Size(max = 100)
    private String nome;
    private String descricao;
    private Boolean ativo = Boolean.FALSE;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
