package com.time02escoladeti.back.usuario;

import com.time02escoladeti.back.pessoa.PessoaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioId> {

    @Query("SELECT u FROM Usuario u WHERE u.pessoaId = :idPessoa")
    Usuario getByIdPessoa(@Param("idPessoa") PessoaId idPessoa);

    @Query("SELECT p.pessoaId FROM Usuario p WHERE p.login = :login AND p.senha = :senha")
    PessoaId getIdPessoaByLogin(@Param("login") String login, @Param("senha") String senha);
}
