package com.time02escoladeti.back.cep;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.endereco.EnderecoId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Cep extends Entidade<CepId> {
    public Cep(int cep, String descricao) {
        this();
        setCep(cep);
        setDescricao(descricao);
    }

    private Cep() {
        super(new CepId());
    }

    @Column(nullable = false)
    private Integer cep;

    @Column(nullable = false)
    private String descricao;

    @NotNull
    public Integer getCep() {
        return cep;
    }

    public void setCep(@NotNull Integer cep) {
        this.cep = cep;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cep cep1 = (Cep) o;

        if (getCep() != null ? !getCep().equals(cep1.getCep()) : cep1.getCep() != null) return false;
        return getDescricao() != null ? getDescricao().equals(cep1.getDescricao()) : cep1.getDescricao() == null;
    }

    @Override
    public int hashCode() {
        int result = getCep() != null ? getCep().hashCode() : 0;
        result = 31 * result + (getDescricao() != null ? getDescricao().hashCode() : 0);
        return result;
    }
}
