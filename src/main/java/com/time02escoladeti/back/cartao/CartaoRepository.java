package com.time02escoladeti.back.cartao;

import com.time02escoladeti.back.pessoa.PessoaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, CartaoId>{

    @Query("SELECT t FROM Cartao t WHERE t.idPessoa = :idPessoa")
    public List<Cartao> findAllByIdPessoa(@Param("idPessoa") PessoaId idPessoa);
}
