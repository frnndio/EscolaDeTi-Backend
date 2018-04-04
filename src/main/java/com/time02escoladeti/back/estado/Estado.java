package com.time02escoladeti.back.estado;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.cidade.Cidade;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estado extends Entidade<EstadoId> {
    @Column
    @Size(max = 150)
    private String nome;

    @Column
    @Size(max = 2)
    private String sigla;

    private Estado() {
        super(new EstadoId());
    }

    public Estado(String sigla, String nome) {
        this();
        setSigla(sigla);
        setNome(nome);
    }

    public String getSigla() {
        return sigla;
    }

    private void setSigla(@NotNull String sigla) {
        this.sigla = sigla;
    }

    @NotNull
    public String getNome() {
        return nome;
    }

    private void setNome(@NotNull String nome) {
        this.nome = nome;
    }
}
