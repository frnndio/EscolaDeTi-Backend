package com.time02escoladeti.back.cidade;

import java.util.List;


import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.estado.EstadoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {
	private CidadeRepository repository;

	@Autowired
	public CidadeService(CidadeRepository repository) {
		this.repository = repository;
	}
	
	public Cidade getPorId(CidadeId id) throws ServiceException {
		return repository.findOne(id);
	}
	
	public List<Cidade> getTodosPorEstado(EstadoId id) throws ServiceException {
		return repository.findByEstadoId(id);
	}
}
