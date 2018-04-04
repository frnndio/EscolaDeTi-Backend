package com.time02escoladeti.back.estado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EstadoService {

    @Autowired
    private EstadoRepository repository;


    public List<Estado> findAll() {
        return repository.findAll();
    }

    public Estado findById(EstadoId id) {
        return repository.findOne(id);
    }
}
