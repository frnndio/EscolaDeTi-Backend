package com.time02escoladeti.back.usuario;

import com.time02escoladeti.back.Entity.Entidade;
import com.time02escoladeti.back.Recursos.Passwords;
import com.time02escoladeti.back.pessoa.PessoaId;
import org.jetbrains.annotations.NotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Usuario extends Entidade<UsuarioId> {

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "id_pessoa", nullable = false))
    private PessoaId pessoaId;

    public Usuario(
            @NotNull String login,
            @NotNull String senha,
            @NotNull PessoaId pessoaId) {
        this();
        setLogin(login);
        setSenha(senha);
        setPessoaId(pessoaId);
    }

    public Usuario(final UsuarioDto usuarioDtoCreator) {
        this(usuarioDtoCreator.getLogin(),
                usuarioDtoCreator.getSenha(),
                usuarioDtoCreator.getIdPessoa());
    }

    private Usuario() {
        super(new UsuarioId());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = Passwords.getHashPassword(senha);
    }

    public PessoaId getPessoaId() {
        return pessoaId;
    }

    private void setPessoaId(PessoaId pessoaId) {
        this.pessoaId = pessoaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (!getLogin().equals(usuario.getLogin())) return false;
        if (!getSenha().equals(usuario.getSenha())) return false;
        return getPessoaId().equals(usuario.getPessoaId());
    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
        result = 31 * result + getSenha().hashCode();
        result = 31 * result + getPessoaId().hashCode();
        return result;
    }
}
