package com.time02escoladeti.back.cidade;

import com.time02escoladeti.back.estado.EstadoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, CidadeId>{

    @Query("SELECT c FROM Cidade c WHERE c.idEstado = :id")
    List<Cidade> findByEstadoId(@Param("id") EstadoId id);
}
