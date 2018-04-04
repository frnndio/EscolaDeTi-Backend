package com.time02escoladeti.back.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioId addUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario(usuarioDto);
        repository.save(usuario);

        return usuario.getId();
    }
}
