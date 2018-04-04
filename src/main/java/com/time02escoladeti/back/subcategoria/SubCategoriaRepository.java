package com.time02escoladeti.back.subcategoria;

import com.time02escoladeti.back.categoria.CategoriaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoriaRepository extends JpaRepository<SubCategoria, SubCategoriaId> {

    @Query("SELECT item FROM SubCategoria item WHERE item.ativo = TRUE AND item.idCategoria = :id ORDER BY item.nome")
    List<SubCategoria> getTodosPorCategoria(@Param("id") CategoriaId id);
    
    @Query("SELECT count(*) FROM SubCategoria")
    Integer countSubCategorias();

    @Query("SELECT s FROM SubCategoria s WHERE s.ativo = TRUE ORDER BY s.nome")
    List<SubCategoria> getAllAtivos();

    @Query("SELECT s FROM SubCategoria s WHERE s.ativo = TRUE AND s.id = :id")
    SubCategoria getByIdAndAtivo(@Param("id") SubCategoriaId subCategoriaId);

}
