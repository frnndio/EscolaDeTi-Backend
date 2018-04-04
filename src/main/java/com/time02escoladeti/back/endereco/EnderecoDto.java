package com.time02escoladeti.back.endereco;

import com.time02escoladeti.back.cep.CepId;
import com.time02escoladeti.back.cidade.CidadeId;

import javax.validation.constraints.Size;

public class EnderecoDto {
    @Size(max = 150)
    private String rua;
    @Size(max = 150)
    private String bairro;
    @Size(max = 10)
    private String numero;
    @Size(max = 200)
    private String complemento;
    private CidadeId idCidade;
    private CepId idCep;

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public CidadeId getIdCidade() {
        return idCidade;
    }

    public CepId getIdCep() {
        return idCep;
    }
}
