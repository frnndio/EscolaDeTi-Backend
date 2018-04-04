package com.time02escoladeti.back.cartao;

import com.time02escoladeti.back.pessoa.PessoaId;

public class CartaoDto {
    private Long numeroCartao;
    private Integer mesValidade;
    private Integer anoValidade;
    private String nomeTitular;
    private PessoaId idPessoa;

    public Long getNumeroCartao() {
        return numeroCartao;
    }

    public Integer getMesValidade() {
        return mesValidade;
    }

    public Integer getAnoValidade() {
        return anoValidade;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public PessoaId getIdPessoa() {
        return idPessoa;
    }
}
