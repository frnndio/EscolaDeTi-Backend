package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.Entity.EntidadeId;
import com.time02escoladeti.back.cartao.Cartao;
import com.time02escoladeti.back.cartao.CartaoController;
import com.time02escoladeti.back.cartao.CartaoId;
import com.time02escoladeti.back.cartao.CartaoRepository;
import com.time02escoladeti.back.pessoa.PessoaFisica;
import com.time02escoladeti.back.pessoa.PessoaId;
import com.time02escoladeti.back.pessoa.PessoaRepository;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Calendar;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartaoControllerTest extends BackApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private CartaoController cartaoController;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Long numeroCartao = 5438208630017384L;
    private static Integer mesValidade = 3;
    private static Integer anoValidade = 2024;
    private static String nomeTitular = "ILIO A O JUNIOR";

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "cartao", "pessoa_fisica");
    }

    @Test
    public void testObterUmaListaDeCartoesSemInformarAPessoa() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/cartoes");
        ResultMatcher result = status().is4xxClientError();

        this.mockMvc.perform(request).andExpect(result);
    }

    @Test
    public void testListaDeCartoesPorUmaPessoaQueNaoExiste() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/cartoes?idPessoa=" + new PessoaId().getValor());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testListaDeCartoesPorUmaPessoa() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        Cartao cartao = criarCartao(pessoa.getId());
        cartaoRepository.save(cartao);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/cartoes?idPessoa=" + pessoa.getId().getValor());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mesValidade").value(mesValidade))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numeroCartao").value(numeroCartao))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeTitular").value(nomeTitular))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].anoValidade").value(anoValidade));
    }

    @Test
    public void testObterUmCartaoPorIdInexistente() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/cartoes/" + new CartaoId().toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testObterUmCartaoPeloId() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        Cartao cartao = criarCartao(pessoa.getId());
        cartaoRepository.save(cartao);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/cartoes/" + cartao.getId().toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.mesValidade").value(mesValidade))
                .andExpect(jsonPath("$.numeroCartao").value(numeroCartao))
                .andExpect(jsonPath("$.nomeTitular").value(nomeTitular))
                .andExpect(jsonPath("$.anoValidade").value(anoValidade));
    }

    @Test
    public void testCadastrarUmCartaoEmBranco() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("");

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCadastrarUmCartaoComMes15() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        JSONObject json = new JSONObject();
        json.put("mesValidade", 15);
        json.put("numeroCartao", numeroCartao);
        json.put("nomeTitular", nomeTitular);
        json.put("anoValidade", anoValidade);
        json.put("idPessoa", pessoa.getId().toString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Mês inválido"));
    }

    @Test
    public void testCadastrarUmCartaoComMesNegativo() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        JSONObject json = new JSONObject();
        json.put("mesValidade", -1);
        json.put("numeroCartao", numeroCartao);
        json.put("nomeTitular", nomeTitular);
        json.put("anoValidade", anoValidade);
        json.put("idPessoa", pessoa.getId().toString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Mês inválido"));
    }

    @Test
    public void testCadastrarUmCartaoComAnoInvalido() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        JSONObject json = new JSONObject();
        json.put("mesValidade", mesValidade);
        json.put("numeroCartao", numeroCartao);
        json.put("nomeTitular", nomeTitular);
        json.put("anoValidade", 2015);
        json.put("idPessoa", pessoa.getId().toString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Ano inválido"));
    }

    @Test
    public void testCadastrarUmCartaoComNumeroCartaoInvalido() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        JSONObject json = new JSONObject();
        json.put("mesValidade", mesValidade);
        json.put("numeroCartao", 0);
        json.put("nomeTitular", nomeTitular);
        json.put("anoValidade", anoValidade);
        json.put("idPessoa", pessoa.getId().toString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Número de cartão inválido"));
    }

    @Test
    public void testCadastrarUmCartao() throws Exception {
        PessoaFisica pessoa = criarPessoa();

        JSONObject json = new JSONObject();
        json.put("mesValidade", mesValidade);
        json.put("numeroCartao", numeroCartao);
        json.put("nomeTitular", nomeTitular);
        json.put("anoValidade", anoValidade);
        json.put("idPessoa", pessoa.getId().toString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());

        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idGerado").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idGerado").isString())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID.fromString(objectRetorno.getString("idGerado"));
    }

    @Test
    public void testExcluirCartaoInexistente() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/cartoes/" + new CartaoId().toString());

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Não existe cartão com esse ID!"));
    }

    @Test
    public void testExcluirCartao() throws Exception {
        PessoaFisica pessoaFisica = criarPessoa();

        Cartao cartao = criarCartao(pessoaFisica.getId());
        cartaoRepository.save(cartao);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/cartoes/" + cartao.getId());

        mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$").doesNotExist());

        Cartao recuperado = cartaoRepository.findOne(cartao.getId());
        assertNull(recuperado);
    }

    private PessoaFisica criarPessoa() {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.set(Calendar.DAY_OF_MONTH, 22);
        dataNascimento.set(Calendar.MONTH, 10);
        dataNascimento.set(Calendar.YEAR, 1996);

        PessoaFisica novaPessoa = new PessoaFisica("Nome", "email@email.com", 10498391965L, dataNascimento.getTime());
        pessoaRepository.save(novaPessoa);
        return novaPessoa;
    }

    private static Cartao criarCartao(PessoaId idPessoa) {
        return new Cartao(numeroCartao, mesValidade, anoValidade, nomeTitular, idPessoa);
    }
}
