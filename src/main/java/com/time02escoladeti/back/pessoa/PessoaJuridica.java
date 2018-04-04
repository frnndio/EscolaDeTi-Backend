package com.time02escoladeti.back.pessoa;

import com.time02escoladeti.back.Enums.InformaçãoTributária;

import javax.persistence.Entity;


@Entity
public class PessoaJuridica extends Pessoa {

    private Long CNPJ;
    private Integer inscricaoEstadual;
    private String nomeResponsavel;
    private InformaçãoTributária informacaoTributaria;

    public PessoaJuridica(String nome, String email, Long CNPJ, String nomeResponsavel) {
        this(nome, email);
        setCNPJ(CNPJ);
        setNomeResponsavel(nomeResponsavel);
    }

    public PessoaJuridica(String nome, String email) {
        super(nome, email);
    }

    private PessoaJuridica() {
        super();
    }

    public Long getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(Long CNPJ) {
        this.CNPJ = CNPJ;
    }

    public Integer getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(Integer inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public InformaçãoTributária getInformacaoTributaria() {
        return informacaoTributaria;
    }

    public void setInformacaoTributaria(InformaçãoTributária informacaoTributaria) {
        this.informacaoTributaria = informacaoTributaria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PessoaJuridica that = (PessoaJuridica) o;

        if (getCNPJ() != null ? !getCNPJ().equals(that.getCNPJ()) : that.getCNPJ() != null) return false;
        if (getInscricaoEstadual() != null ? !getInscricaoEstadual().equals(that.getInscricaoEstadual()) : that.getInscricaoEstadual() != null)
            return false;
        if (getNomeResponsavel() != null ? !getNomeResponsavel().equals(that.getNomeResponsavel()) : that.getNomeResponsavel() != null)
            return false;
        return getInformacaoTributaria() == that.getInformacaoTributaria();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCNPJ() != null ? getCNPJ().hashCode() : 0);
        result = 31 * result + (getInscricaoEstadual() != null ? getInscricaoEstadual().hashCode() : 0);
        result = 31 * result + (getNomeResponsavel() != null ? getNomeResponsavel().hashCode() : 0);
        result = 31 * result + (getInformacaoTributaria() != null ? getInformacaoTributaria().hashCode() : 0);
        return result;
    }
}
