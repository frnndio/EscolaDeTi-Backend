package com.time02escoladeti.back.endereco;

import java.util.List;

import com.time02escoladeti.back.cep.Cep;
import com.time02escoladeti.back.cep.CepService;
import com.time02escoladeti.back.cidade.Cidade;
import com.time02escoladeti.back.cidade.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;

@Service
@Transactional
public class EnderecoService {

    private final EnderecoRepository repo;
    private final CidadeService cidadeService;
    private final CepService cepService;

    @Autowired
    public EnderecoService(EnderecoRepository repo, CidadeService cidadeService, CepService cepService) {
        this.repo = repo;
        this.cidadeService = cidadeService;
        this.cepService = cepService;
    }

    public Endereco getEnderecoById(EnderecoId id) throws ServiceException {
        return repo.findOne(id);
    }

    public EnderecoId postEndereco(EnderecoDto enderecoDto) throws ServiceException {
        Cidade cidade = cidadeService.getPorId(enderecoDto.getIdCidade());
        Cep cep = cepService.getPorId(enderecoDto.getIdCep());

        Endereco endereco = new Endereco(enderecoDto.getRua(), enderecoDto.getBairro(), enderecoDto.getNumero(), cidade, cep);
        endereco.setComplemento(enderecoDto.getComplemento());

        repo.save(endereco);
        return endereco.getId();
    }

    public void putEndereco(EnderecoId id, EnderecoDto enderecoDto) throws ServiceException {
        Endereco endereco = repo.findOne(id);
        endereco.setRua(enderecoDto.getRua());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setComplemento(enderecoDto.getComplemento());
        endereco.setBairro(enderecoDto.getBairro());
        repo.save(endereco);
    }

    public void deleteEndereco(EnderecoId id) throws ServiceException {
        repo.delete(id);
    }

}
