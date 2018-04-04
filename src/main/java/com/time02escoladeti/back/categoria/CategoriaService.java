package com.time02escoladeti.back.categoria;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.foto.FotoId;
import com.time02escoladeti.back.foto.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    @Autowired
    private FotoService fotoService;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> getTodos() throws ServiceException {
        return repository.findAtivos();
    }

    public Categoria getPorId(CategoriaId id) throws ServiceException {
        return repository.findAtivo(id);
    }

    public CategoriaId salvar(CategoriaDto categoriaDto) throws ServiceException {
        final Categoria novaCategoria = new Categoria(categoriaDto);
        repository.save(novaCategoria);
        return novaCategoria.getId();
    }

    public void atualizar(CategoriaId id, CategoriaDto categoriaDto) throws ServiceException {
        final Categoria entidade = repository.findOne(id);
        entidade.setNome(categoriaDto.getNome());
        entidade.setDescricao(categoriaDto.getDescricao());
        entidade.setAtivo(categoriaDto.getAtivo());
        repository.save(entidade);
    }

    public void deletar(CategoriaId id) throws ServiceException {
        repository.delete(id);
    }

    public FotoId cadastrarFoto(CategoriaId idCategoria, MultipartFile foto) throws IOException {

        FotoId idFoto = fotoService.cadastrar(foto);

        Categoria categoria = repository.findOne(idCategoria);
        categoria.setIdFoto(idFoto);

        repository.save(categoria);

        return idFoto;
    }
}
