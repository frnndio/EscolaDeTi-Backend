package com.time02escoladeti.back.usuario;

import com.time02escoladeti.back.pessoa.PessoaId;

public class UsuarioDto {
    private String login;
    private String senha;
    private PessoaId idPessoa;

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public PessoaId getIdPessoa() {
        return idPessoa;
    }
}
