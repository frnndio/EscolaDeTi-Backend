package com.time02escoladeti.back.endereco;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.cep.Cep;
import com.time02escoladeti.back.cidade.Cidade;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class Endereco extends Entidade<EnderecoId> {
    private Endereco() {
        super(new EnderecoId());
    }

    public Endereco(String rua, String bairro, String numero, Cidade cidade, Cep cep) {
        this();
        setRua(rua);
        setBairro(bairro);
        setNumero(numero);
        setCidade(cidade);
        setCep(cep);
    }

    @Column
    @Size(max = 150)
    private String rua;

    @Column
    @Size(max = 150)
    private String bairro;

    @Column
    @Size(max = 10)
    private String numero;

    @Column
    @Size(max = 200)
    private String complemento;

    @OneToOne(optional = false)
    private Cidade cidade;

    @OneToOne(optional = false)
    private Cep cep;

    @NotNull
    public String getRua() {
        return rua;
    }

    public void setRua(@NotNull String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(@NotNull String bairro) {
        this.bairro = bairro;
    }

    @NotNull
    public String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(@NotNull Cidade cidade) {
        this.cidade = cidade;
    }

    public Cep getCep() {
        return cep;
    }

    public void setCep(@NotNull Cep cep) {
        this.cep = cep;
    }
}
