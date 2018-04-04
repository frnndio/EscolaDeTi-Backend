package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.foto.Foto;
import com.time02escoladeti.back.subcategoria.SubCategoriaId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Entity
public class AnuncioMaquinario extends Anuncio {

    @NotNull
    @Column(nullable = false)
    private String marca;

    @NotNull
    @Column(nullable = false)
    private Integer ano;

    public AnuncioMaquinario(@NotNull String titulo,
                             @NotNull String descricao,
                             @NotNull Long telefone,
                             @NotNull SubCategoriaId subCategoriaId,
                             @NotNull String marca,
                             @NotNull Integer ano) {
        super(titulo, descricao, telefone, subCategoriaId);
        this.marca = marca;
        this.ano = ano;
    }

    private AnuncioMaquinario() {
        super();
    }

    public String getMarca() {
        return marca;
    }

    public AnuncioMaquinario setMarca(String marca) {
        this.marca = marca;
        return this;
    }

    public Integer getAno() {
        return ano;
    }

    public AnuncioMaquinario setAno(Integer ano) {
        this.ano = ano;
        return this;
    }

    public List<Foto> getFotos() {
        return Collections.unmodifiableList(fotos);
    }

    public void adicionarFoto(final Foto foto) {
        fotos.add(foto);
    }
}
