package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.Recursos.Passwords;
import com.time02escoladeti.back.pessoa.PessoaFisica;
import com.time02escoladeti.back.pessoa.PessoaId;
import com.time02escoladeti.back.pessoa.PessoaRepository;
import com.time02escoladeti.back.usuario.Usuario;
import com.time02escoladeti.back.usuario.UsuarioController;
import com.time02escoladeti.back.usuario.UsuarioId;
import com.time02escoladeti.back.usuario.UsuarioRepository;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sun.security.provider.MD5;
import sun.security.util.Password;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsuarioControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "usuario", "pessoa", "pessoa_fisica", "pessoa_juridica");
    }

    @Test
    public void testCadastrarNovoUsuario() throws Exception {
        PessoaFisica pessoa = new PessoaFisica("Nome", "email@email.com");
        pessoaRepository.save(pessoa);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", "email@email.com");
        jsonObject.put("senha", "senhasenhada");
        jsonObject.put("idPessoa", pessoa.getId().toString());

        MockHttpServletRequestBuilder request = post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonObject.toString());

        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.idGerado").isString())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID uuidGerado = UUID.fromString(objectRetorno.getString("idGerado"));

        UsuarioId usuarioId = new UsuarioId(uuidGerado.toString());

        Usuario recuperado = usuarioRepository.findOne(usuarioId);
        assertEquals(recuperado.getLogin(), "email@email.com");
        assertEquals(recuperado.getSenha(), Passwords.getHashPassword("senhasenhada"));
        assertEquals(recuperado.getPessoaId(), pessoa.getId());
    }

    @Test
    public void testUsuarioIgualAOutro() throws Exception {
        PessoaId pessoaId = new PessoaId();

        Usuario u1 = new Usuario("teste", "teste", pessoaId);
        Usuario u2 = new Usuario("teste", "teste", pessoaId);

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}
