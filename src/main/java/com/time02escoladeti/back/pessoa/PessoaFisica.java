package com.time02escoladeti.back.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.time02escoladeti.back.Enums.Sexo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Entity
public class PessoaFisica extends Pessoa {

    @Column
    private Integer RG;

    @Column
    private Long CPF;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @Enumerated(value = EnumType.STRING)
    private Sexo sexo;

    public PessoaFisica(String nome, String email, Long CPF, Date dataNascimento) {
        this(nome, email);
        setCPF(CPF);
        setDataNascimento(dataNascimento);
    }

    public PessoaFisica(String nome, String email) {
        super(nome, email);
    }

    private PessoaFisica() {
        super();
    }

    public Integer getRG() {
        return RG;
    }

    public void setRG(Integer RG) {
        this.RG = RG;
    }

    public Long getCPF() {
        return CPF;
    }

    public void setCPF(Long CPF) {
        this.CPF = CPF;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PessoaFisica that = (PessoaFisica) o;

        if (getRG() != null ? !getRG().equals(that.getRG()) : that.getRG() != null) return false;
        if (!getCPF().equals(that.getCPF())) return false;
        if (!getDataNascimento().equals(that.getDataNascimento())) return false;
        return sexo == that.sexo;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getRG() != null ? getRG().hashCode() : 0);
        result = 31 * result + (getCPF() != null ? getCPF().hashCode() : 0);
        result = 31 * result + (getDataNascimento() != null ? getDataNascimento().hashCode() : 0);
        result = 31 * result + (getSexo() != null ? getSexo().hashCode() : 0);
        return result;
    }
}
