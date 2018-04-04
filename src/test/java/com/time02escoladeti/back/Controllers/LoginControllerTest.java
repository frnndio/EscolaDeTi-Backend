package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.login.LoginController;
import com.time02escoladeti.back.pessoa.PessoaFisica;
import com.time02escoladeti.back.pessoa.PessoaRepository;
import com.time02escoladeti.back.usuario.Usuario;
import com.time02escoladeti.back.usuario.UsuarioRepository;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private LoginController loginController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "usuario", "pessoa", "pessoa_fisica", "pessoa_juridica");
    }

    @Test
    public void testLoginInvalido() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", "login qualquer");
        jsonObject.put("senha", "uma senha qualquer");

        MockHttpServletRequestBuilder request = post("/login/logar")
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    public void testLoginValido() throws Exception {
        PessoaFisica pessoa = new PessoaFisica("ilio", "ilio");
        Usuario usuario = new Usuario("login", "senha", pessoa.getId());

        pessoaRepository.save(pessoa);
        usuarioRepository.save(usuario);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", "login");
        jsonObject.put("senha", "senha");

        MockHttpServletRequestBuilder request = post("/login/logar")
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPessoa").value(pessoa.getId().toString()));
    }

    @Test
    public void testRecuperarSenhaEmailQueNaoExiste() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "ilioadriano@live.com");

        MockHttpServletRequestBuilder request = post("/login/recuperar")
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc
                .perform(request)
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testRecuperarSenhaEmailQueExiste() throws Exception {
        PessoaFisica pessoa = new PessoaFisica("ilio", "ilioadriano@live.com");
        Usuario usuario = new Usuario("login", "senha", pessoa.getId());

        pessoaRepository.save(pessoa);
        usuarioRepository.save(usuario);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "ilioadriano@live.com");

        MockHttpServletRequestBuilder request = post("/login/recuperar")
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
