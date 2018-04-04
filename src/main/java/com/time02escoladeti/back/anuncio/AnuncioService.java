package com.time02escoladeti.back.anuncio;

import com.time02escoladeti.back.Enums.CategoriaAnúncio;
import com.time02escoladeti.back.Enums.StatusAnúncio;
import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.usuario.UsuarioId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository repository;

    @Autowired
    private AnuncioMaquinarioRepository repositoryMaquinario;

    public List<Anuncio> findAll() throws ServiceException {
        List<Anuncio> resultado = repository.findAll();
        resultado.subList(0, 12);
        return resultado;
    }

    public List<Anuncio> findAllAnunciosPublicados(UsuarioId id) throws ServiceException {
        List<Anuncio> resultado = repository.findAllAnunciosPublicados(id);
        resultado.subList(0, 12);
        return resultado;
    }

    public List<Anuncio> findAllAnunciosAguardandoAprovacao(UsuarioId id) throws ServiceException {
        List<Anuncio> resultado = repository.findAllAnunciosAguardandoAprovacao(id);
        resultado.subList(0, 12);
        return resultado;
    }

    public List<Anuncio> findAllAnunciosExpirados(UsuarioId id) throws ServiceException {
        List<Anuncio> resultado = repository.findAllAnunciosExpirados(id);
        resultado.subList(0, 12);
        return resultado;
    }

    public List<Anuncio> getAnuncioImpulsionados(UsuarioId id) throws ServiceException {
        List<Anuncio> resultado = repository.findAllAnunciosImpulsionados(id);
        resultado.subList(0, 12);
        return resultado;
    }

    public List<Anuncio> getAllAnuncioImpulsionado() throws ServiceException {
        List<Anuncio> resultado = repository.getAllAnuncioImpulsionado();
        resultado.subList(0, 12);
        return resultado;
    }

    public Anuncio findAnuncioById(AnuncioId id) throws ServiceException {
        return repository.findOne(id);
    }

    public AnuncioId excluirAnuncio(AnuncioId id) throws ServiceException {
        repository.delete(id);
        return id;
    }

    public AnuncioId createAnuncio(AnuncioMaquinarioDto anuncio) throws ServiceException {
        AnuncioMaquinario entidade = new AnuncioMaquinario(anuncio.getTitulo(), anuncio.getDescricao(), anuncio.getTelefone(), anuncio.getIdSubCategoria(), anuncio.getMarca(), anuncio.getAno());
        entidade.setValor(anuncio.getValor());
        entidade.setNegociavel(anuncio.getNegociavel());
        entidade.setDataCadastro(anuncio.getDataCadastro());
        entidade.setStatusAnuncio(StatusAnúncio.valueOf(anuncio.getStatusAnuncio()));
        entidade.setCategoriaAnuncio(CategoriaAnúncio.valueOf(anuncio.getCategoriaAnuncio()));
        repositoryMaquinario.save(entidade);
        return entidade.getId();
    }

    public AnuncioId updateAnuncio(AnuncioId idAnuncio, AnuncioMaquinarioDto anuncio) throws ServiceException {
        final Anuncio entidade = repository.findOne(idAnuncio);
        repository.save(entidade);
        return entidade.getId();
    }

    public Page<AnuncioMaquinario> find(Integer limit, Integer page) {
        return repositoryMaquinario.findAll(createPageable(limit, page));

    }

    private PageRequest createPageable(Integer limit, Integer page) {
        return new PageRequest(page, limit);
    }

}
