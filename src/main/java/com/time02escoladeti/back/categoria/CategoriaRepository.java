package com.time02escoladeti.back.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, CategoriaId>{

    @Query("SELECT t FROM Categoria t WHERE t.ativo = TRUE")
    List<Categoria> findAtivos();

    @Query("SELECT t FROM Categoria t WHERE t.ativo = TRUE AND t.id = :idCategoria")
    Categoria findAtivo(@Param("idCategoria") CategoriaId idCategoria);
}
