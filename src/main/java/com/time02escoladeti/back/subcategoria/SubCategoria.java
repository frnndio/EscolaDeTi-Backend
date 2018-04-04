package com.time02escoladeti.back.subcategoria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.categoria.CategoriaId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
public class SubCategoria extends Entidade<SubCategoriaId> {

    @NotNull
    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "id_categoria", nullable = false))
    private CategoriaId idCategoria;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @JsonCreator
    public SubCategoria(@JsonProperty("idCategoria")
                        @NotNull
                                CategoriaId idCategoria,
                        @NotNull
                                String nome) {
        this();
        setIdCategoria(idCategoria);
        setNome(nome);
    }

    public SubCategoria(final SubCategoriaDto subCategoriaDtoCreator) {
        this(subCategoriaDtoCreator.getIdCategoria(), subCategoriaDtoCreator.getNome());
        setDescricao(subCategoriaDtoCreator.getDescricao());
        setAtivo(subCategoriaDtoCreator.getAtivo());
    }

    private SubCategoria() {
        super(new SubCategoriaId());
    }

    public CategoriaId getIdCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SubCategoria setIdCategoria(CategoriaId idCategoria) {
        this.idCategoria = idCategoria;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public SubCategoria setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public SubCategoria setAtivo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }
}
