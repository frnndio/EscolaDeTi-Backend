package com.time02escoladeti.back.login;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.Recursos.Passwords;
import com.time02escoladeti.back.pessoa.Pessoa;
import com.time02escoladeti.back.pessoa.PessoaId;
import com.time02escoladeti.back.pessoa.PessoaRepository;
import com.time02escoladeti.back.usuario.Usuario;
import com.time02escoladeti.back.usuario.UsuarioId;
import com.time02escoladeti.back.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaId logar(LoginDto login) throws ServiceException {
        PessoaId idPessoa = repository.getIdPessoaByLogin(login.getLogin(), Passwords.getHashPassword(login.getSenha()));
        if (idPessoa == null) {
            throw new ServiceException("Usuário não encontrado!");
        }

        return idPessoa;
    }

    public void recuperarSenha(String email) throws ServiceException {
        Pessoa pessoa = pessoaRepository.getByEmail(email);
        if (pessoa == null) {
            throw new ServiceException("Email não encontrado", HttpStatus.NOT_FOUND);
        }

        Usuario entidade = repository.getByIdPessoa(pessoa.getId());
        
        //Fazer a troca de senha;
        repository.save(entidade);
    }
}
