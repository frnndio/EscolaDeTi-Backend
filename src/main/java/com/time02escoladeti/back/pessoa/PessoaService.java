package com.time02escoladeti.back.pessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PessoaService {
    private final PessoaRepository repositoryGeral;

    @Autowired
    public PessoaService(PessoaRepository repositoryGeral) {
        this.repositoryGeral = repositoryGeral;
    }

    public Pessoa getById(PessoaId id) {
        Pessoa one = repositoryGeral.findOne(id);
        return one;
    }

    public PessoaId salvar(Pessoa novaPessoa) {
        repositoryGeral.save(novaPessoa);
        return novaPessoa.getId();
    }

    public void editar(PessoaId idPessoa, PessoaFisica pessoaFisica) {
        PessoaFisica pessoa = (PessoaFisica) this.getById(idPessoa);
        pessoa.setNome(pessoaFisica.getNome());
        pessoa.setEmail(pessoaFisica.getEmail());
        pessoa.setTelefone(pessoaFisica.getTelefone());
        pessoa.setCelular(pessoaFisica.getCelular());
        pessoa.setCPF(pessoaFisica.getCPF());
        pessoa.setRG(pessoaFisica.getRG());
        pessoa.setDataNascimento(pessoaFisica.getDataNascimento());
        pessoa.setSexo(pessoaFisica.getSexo());
        repositoryGeral.save(pessoa);
    }

    public void editar(PessoaId idPessoa, PessoaJuridica pessoaFisica) {
        PessoaJuridica pessoa = (PessoaJuridica) this.getById(idPessoa);
        pessoa.setNome(pessoaFisica.getNome());
        pessoa.setEmail(pessoaFisica.getEmail());
        pessoa.setTelefone(pessoaFisica.getTelefone());
        pessoa.setCelular(pessoaFisica.getCelular());
        pessoa.setCNPJ(pessoaFisica.getCNPJ());
        pessoa.setNomeResponsavel(pessoaFisica.getNomeResponsavel());
        pessoa.setInformacaoTributaria(pessoaFisica.getInformacaoTributaria());
        pessoa.setInscricaoEstadual(pessoaFisica.getInscricaoEstadual());
        repositoryGeral.save(pessoa);
    }
}
