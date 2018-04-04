package com.time02escoladeti.back.login;

import com.time02escoladeti.back.Entity.PessoaIdLoginRetorno;
import com.time02escoladeti.back.Recursos.Excecoes.ServiceException;
import com.time02escoladeti.back.pessoa.PessoaId;
import com.time02escoladeti.back.usuario.UsuarioId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {
    private final LoginService service;

    @Autowired
    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/logar")
    public ResponseEntity logar(@RequestBody LoginDto login) {
        try {
            PessoaId resultado = service.logar(login);
            return ResponseEntity.ok(new PessoaIdLoginRetorno(resultado));
        } catch (ServiceException excecao) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/recuperar")
    public ResponseEntity recuperarSenha(@RequestBody RecuperarLoginDTO loginDTO) {
        try {
            service.recuperarSenha(loginDTO.getEmail());
            return ResponseEntity.ok().build();
        } catch (ServiceException excecao) {
            return ResponseEntity.status(excecao.getCodigoStatus()).body(excecao.getMessage());
        }
    }
}
