package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.Enums.CategoriaAnúncio;
import com.time02escoladeti.back.Enums.StatusAnúncio;
import com.time02escoladeti.back.subcategoria.SubCategoriaId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class AnuncioMaquinarioDto {
    //private AnuncioId id;
    private SubCategoriaId idSubCategoria;
    private String titulo;
    private String descricao;
    //private EstadoId idEstado;
    //private CidadeId idCidade;
    private Long telefone;
    private String marca;
    private Integer ano;
    private Float valor = 0f;
    //private ModoCobranca modoDeCobranca;
    private Boolean negociavel = Boolean.FALSE;
    private Date dataCadastro;
    private String statusAnuncio;
    private String categoriaAnuncio;

    public SubCategoriaId getIdSubCategoria() {
        return idSubCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public Integer getAno() {
        return ano;
    }

    public Float getValor() {
        return valor;
    }

    public Boolean getNegociavel() {
        return negociavel;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public Long getTelefone() {
        return telefone;
    }

    public String getStatusAnuncio() {
        return statusAnuncio;
    }

    public String getCategoriaAnuncio() {
        return categoriaAnuncio;
    }
}
