package com.time02escoladeti.back.subcategoria;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.categoria.CategoriaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoriaService {
    @Autowired
    private SubCategoriaRepository repository;

    public List<SubCategoria> getTodos() {
        return repository.getAllAtivos();
    }

    public SubCategoria getPorId(SubCategoriaId subCategoriaId) throws ServiceException {
        SubCategoria byIdAndAtivo = repository.getByIdAndAtivo(subCategoriaId);
        if (byIdAndAtivo == null) {
            throw new ServiceException("Não encontrado", HttpStatus.NOT_FOUND);
        }

        return byIdAndAtivo;
    }

    public List<SubCategoria> getTodosPorCategoria(CategoriaId idCategoria) throws ServiceException {
        List<SubCategoria> result = repository.getTodosPorCategoria(idCategoria);
        //result.subList(0, 12);
        return result;
    }

    public SubCategoriaId cadastrarNovo(SubCategoriaDto subCategoriaDto) throws ServiceException {
        final SubCategoria novaSubCategoria = new SubCategoria(subCategoriaDto);
        repository.save(novaSubCategoria);
        return novaSubCategoria.getId();
    }

    public void atualizar(SubCategoriaId id, SubCategoriaDto subCategoriaDto) throws ServiceException {
        SubCategoria entidade = repository.findOne(id);
        entidade.setNome(subCategoriaDto.getNome());
        entidade.setDescricao(subCategoriaDto.getDescricao());
        entidade.setAtivo(subCategoriaDto.getAtivo());
        repository.save(entidade);
    }

    public void deletar(SubCategoriaId id) throws ServiceException {
        repository.delete(id);
    }
}
