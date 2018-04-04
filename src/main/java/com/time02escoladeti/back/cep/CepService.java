package com.time02escoladeti.back.cep;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CepService {
    private final CepRepository repository;

    @Autowired
    public CepService(CepRepository repository) {
        this.repository = repository;
    }

    public List<Cep> getTodos() throws ServiceException {
        return repository.findAll();
    }

    public Cep getPorId(CepId id) throws ServiceException {
        return repository.findOne(id);
    }
}