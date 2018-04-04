package com.time02escoladeti.back.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PessoaRepository extends JpaRepository<Pessoa, PessoaId> {

    @Query("SELECT p FROM Pessoa p WHERE p.email = :email")
    public Pessoa getByEmail(@Param("email") String email);
}
