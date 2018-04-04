package com.time02escoladeti.back.cartao;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.pessoa.PessoaId;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Calendar;

@Entity
public class Cartao extends Entidade<CartaoId> {
    @Column(nullable = false)
    private Long numeroCartao;

    @Column(nullable = false)
    private Integer mesValidade;

    @Column(nullable = false)
    private Integer anoValidade;

    @Column(nullable = false)
    private String nomeTitular;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "id_pessoa", nullable = false))
    private PessoaId idPessoa;

    private Cartao() {
        super(new CartaoId());
    }

    public Cartao(Long numeroCartao, Integer mesValidade, Integer anoValidade, String nomeTitular, PessoaId idPessoa) {
        this();
        setNumeroCartao(numeroCartao);
        setMesValidade(mesValidade);
        setAnoValidade(anoValidade);
        setNomeTitular(nomeTitular);
        setIdPessoa(idPessoa);
    }

    public Long getNumeroCartao() {
        return numeroCartao;
    }

    public Cartao setNumeroCartao(@NotNull Long numero) {
        if (numero <= 0) {
            throw new ServiceException("Número de cartão inválido");
        }

        this.numeroCartao = numero;
        return this;
    }

    public Integer getMesValidade() {
        return mesValidade;
    }

    public Cartao setMesValidade(@NotNull Integer mesValidade) {
        if (mesValidade < 1 || mesValidade > 12) {
            throw new ServiceException("Mês inválido");
        }
        this.mesValidade = mesValidade;
        return this;
    }

    public Integer getAnoValidade() {
        return anoValidade;
    }

    public Cartao setAnoValidade(@NotNull Integer anoValidade) {
        if (anoValidade < Calendar.getInstance().get(Calendar.YEAR)) {
            throw new ServiceException("Ano inválido");
        }
        this.anoValidade = anoValidade;
        return this;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public Cartao setNomeTitular(@NotNull String nomeTitular) {
        this.nomeTitular = nomeTitular;
        return this;
    }

    @NotNull
    public PessoaId getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(@NotNull PessoaId idPessoa) {
        this.idPessoa = idPessoa;
    }
}
