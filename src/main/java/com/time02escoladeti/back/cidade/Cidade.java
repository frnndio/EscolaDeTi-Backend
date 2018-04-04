package com.time02escoladeti.back.cidade;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.estado.EstadoId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Cidade extends Entidade<CidadeId> {
    @Column
    @Size(max = 150)
    private String nome;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "id_estado"))
    private EstadoId idEstado;

    private Cidade() {
        super(new CidadeId());
    }

    public Cidade(EstadoId idEstado, String nome) {
        this();
        setIdEstado(idEstado);
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    private void setNome(@NotNull String nome) {
        this.nome = nome;
    }

    @NotNull
    public EstadoId getIdEstado() {
        return idEstado;
    }

    private void setIdEstado(@NotNull EstadoId idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cidade cidade = (Cidade) o;

        if (getNome() != null ? !getNome().equals(cidade.getNome()) : cidade.getNome() != null) return false;
        return getIdEstado() != null ? getIdEstado().equals(cidade.getIdEstado()) : cidade.getIdEstado() == null;
    }

    @Override
    public int hashCode() {
        int result = getNome() != null ? getNome().hashCode() : 0;
        result = 31 * result + (getIdEstado() != null ? getIdEstado().hashCode() : 0);
        return result;
    }
}
