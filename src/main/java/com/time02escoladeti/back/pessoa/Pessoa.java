package com.time02escoladeti.back.pessoa;

import com.time02escoladeti.back.Entity.Entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends Entidade<PessoaId> {
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column
    private Long telefone;

    @Column
    private Long celular;

    public Pessoa(String nome, String email) {
        this();
        setNome(nome);
        setEmail(email);
    }

    protected Pessoa() {
        super(new PessoaId());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pessoa pessoa = (Pessoa) o;

        if (!getNome().equals(pessoa.getNome())) return false;
        if (!getEmail().equals(pessoa.getEmail())) return false;
        if (getTelefone() != null ? !getTelefone().equals(pessoa.getTelefone()) : pessoa.getTelefone() != null)
            return false;
        return getCelular() != null ? getCelular().equals(pessoa.getCelular()) : pessoa.getCelular() == null;
    }

    @Override
    public int hashCode() {
        int result = getNome().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + (getTelefone() != null ? getTelefone().hashCode() : 0);
        result = 31 * result + (getCelular() != null ? getCelular().hashCode() : 0);
        return result;
    }
}
