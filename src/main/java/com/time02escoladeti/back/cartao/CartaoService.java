package com.time02escoladeti.back.cartao;

import java.util.List;

import com.time02escoladeti.back.pessoa.PessoaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;

@Service
@Transactional
public class CartaoService {
    private final CartaoRepository repositorio;

    @Autowired
    public CartaoService(CartaoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Cartao> getAllCartoes(PessoaId idPessoa) {
        return repositorio.findAllByIdPessoa(idPessoa);
    }

    public Cartao getCartaoById(CartaoId id) {
        return repositorio.findOne(id);
    }

    public CartaoId postCartao(CartaoDto cartao) {
        Cartao novoCartao = new Cartao(cartao.getNumeroCartao(),
                cartao.getMesValidade(),
                cartao.getAnoValidade(),
                cartao.getNomeTitular(),
                cartao.getIdPessoa());

        repositorio.save(novoCartao);
        return novoCartao.getId();
    }

    public void deleteCartao(CartaoId id) {
        repositorio.delete(id);
    }
}
