package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.Enums.CategoriaAnúncio;
import com.time02escoladeti.back.Enums.StatusAnúncio;
import com.time02escoladeti.back.categoria.CategoriaId;
import com.time02escoladeti.back.endereco.Endereco;
import com.time02escoladeti.back.foto.Foto;
import com.time02escoladeti.back.subcategoria.SubCategoriaId;
import com.time02escoladeti.back.usuario.Usuario;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Anuncio extends Entidade<AnuncioId> {

    @NotNull
    @Column(nullable = false)
    private String titulo;

    @NotNull
    @Column(nullable = false)
    private String descricao;

    @NotNull
    @Column(nullable = false)
    private Long telefone;

    @Column
    private Date validade;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean impulsionado = Boolean.FALSE;

    @Embedded
    @NotNull
    @AttributeOverride(name = "valor", column = @Column(name = "idSubCategoria", nullable = false))
    private SubCategoriaId subCategoriaId;

    @Enumerated(EnumType.STRING)
    private StatusAnúncio statusAnuncio;

    @Enumerated(EnumType.STRING)
    private CategoriaAnúncio categoriaAnuncio;

    @NotNull
    @Column(nullable = false, columnDefinition = "DECIMAL(8, 3) DEFAULT 0")
    private Float valor = 0f;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean negociavel = Boolean.FALSE;

    @Column
    private Integer quantidadeDeVisitas = 0;

    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "id_anuncio")}, inverseJoinColumns = {@JoinColumn(name = "id_foto")})
    List<Foto> fotos = new ArrayList<>();

    @OneToOne(optional = false)
    private Usuario usuario;

    @Column
    private Date dataCadastro;

    @OneToOne(optional=false)
    private Endereco endereco;

    public Anuncio(@NotNull String titulo, @NotNull String descricao, @NotNull Long telefone, @NotNull SubCategoriaId subCategoriaId) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        this.telefone = telefone;
        this.subCategoriaId = subCategoriaId;
    }

    protected Anuncio() {
        super(new AnuncioId());
    }

    public String getTitulo() {
        return titulo;
    }

    public Anuncio setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Anuncio setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Long getTelefone() {
        return telefone;
    }

    public Anuncio setTelefone(Long telefone) {
        this.telefone = telefone;
        return this;
    }

    public Date getValidade() {
        return validade;
    }

    public Anuncio setValidade(Date validade) {
        this.validade = validade;
        return this;
    }

    public Boolean getImpulsionado() {
        return impulsionado;
    }

    public Anuncio setImpulsionado(Boolean impulsionado) {
        this.impulsionado = impulsionado;
        return this;
    }

    public StatusAnúncio getStatusAnuncio() {
        return statusAnuncio;
    }

    public Anuncio setStatusAnuncio(StatusAnúncio statusAnuncio) {
        this.statusAnuncio = statusAnuncio;
        return this;
    }

    public CategoriaAnúncio getCategoriaAnuncio() {
        return categoriaAnuncio;
    }

    public Anuncio setCategoriaAnuncio(CategoriaAnúncio categoriaAnuncio) {
        this.categoriaAnuncio = categoriaAnuncio;
        return this;
    }

    public SubCategoriaId getSubCategoriaId() {
        return subCategoriaId;
    }

    public Anuncio setSubCategoriaId(SubCategoriaId subCategoriaId) {
        this.subCategoriaId = subCategoriaId;
        return this;
    }

    public Float getValor() {
        return valor;
    }

    public Anuncio setValor(Float valor) {
        this.valor = valor;
        return this;
    }

    public Boolean getNegociavel() {
        return negociavel;
    }

    public Anuncio setNegociavel(Boolean negociavel) {
        this.negociavel = negociavel;
        return this;
    }

    public Integer getQuantidadeDeVisitas() {
        return quantidadeDeVisitas;
    }

    public Anuncio setQuantidadeDeVisitas(Integer quantidadeDeVisitas) {
        this.quantidadeDeVisitas = quantidadeDeVisitas;
        return this;
    }

    public List<Foto> getFotos() {
        return Collections.unmodifiableList(fotos);
    }

    public void adicionarFoto(final Foto foto) {
        fotos.add(foto);
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public Anuncio setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
