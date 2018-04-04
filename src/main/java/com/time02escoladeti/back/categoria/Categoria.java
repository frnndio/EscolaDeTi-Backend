package com.time02escoladeti.back.categoria;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.foto.Foto;
import com.time02escoladeti.back.foto.FotoId;
import com.time02escoladeti.back.subcategoria.SubCategoria;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria extends Entidade<CategoriaId> {
    @Column(nullable = false)
    private String nome;
    @Column
    private String descricao;
    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @AttributeOverride(name = "valor", column = @Column(name = "idFoto"))
    private FotoId idFoto;

    public Categoria(final CategoriaDto categoriaDto) {
        this(categoriaDto.getNome());
        setDescricao(categoriaDto.getDescricao());
        setAtivo(categoriaDto.getAtivo());
    }

    public Categoria(@NotNull String nome) {
        this();
        setNome(nome);
    }

    private Categoria() {
        super(new CategoriaId());
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Categoria setAtivo(@NotNull Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public FotoId getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(FotoId idFoto) {
        this.idFoto = idFoto;
    }
}
