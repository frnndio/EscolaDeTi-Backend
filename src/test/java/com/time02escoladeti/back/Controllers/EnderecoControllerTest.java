package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.cep.Cep;
import com.time02escoladeti.back.cep.CepRepository;
import com.time02escoladeti.back.cidade.Cidade;
import com.time02escoladeti.back.cidade.CidadeRepository;
import com.time02escoladeti.back.endereco.Endereco;
import com.time02escoladeti.back.endereco.EnderecoController;
import com.time02escoladeti.back.endereco.EnderecoId;
import com.time02escoladeti.back.endereco.EnderecoRepository;
import com.time02escoladeti.back.estado.Estado;
import com.time02escoladeti.back.estado.EstadoRepository;
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

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EnderecoControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private EnderecoController enderecoController;

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CepRepository cepRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(enderecoController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "endereco", "cep", "cidade", "estado");
    }

    @Test
    public void testInserirUmEndereco() throws Exception {
        Estado estado = new Estado("PR", "Paraná");
        estadoRepository.save(estado);

        Cidade maringa = new Cidade(estado.getId(), "Maringá");
        cidadeRepository.save(maringa);

        Cep cep = new Cep(87140000, "Cep cepado");
        cepRepository.save(cep);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rua", "Rua avião");
        jsonObject.put("bairro", "Bairro avião");
        jsonObject.put("numero", "159");
        jsonObject.put("idCidade", maringa.getId().toString());
        jsonObject.put("idCep", cep.getId().toString());
        jsonObject.put("complemento", "Complemento azul");

        MockHttpServletRequestBuilder request = post("/enderecos")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonObject.toString());

        String jsonResponse = mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.idGerado").isString())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonResponse);
        UUID entidadeId = UUID.fromString(objectRetorno.getString("idGerado"));

        EnderecoId enderecoId = new EnderecoId(entidadeId.toString());
        Endereco enderecoRecuperado = enderecoRepository.findOne(enderecoId);

        assertEquals(enderecoRecuperado.getCidade(), maringa);
        assertEquals(enderecoRecuperado.getCep(), cep);
        assertEquals(enderecoRecuperado.getRua(), "Rua avião");
        assertEquals(enderecoRecuperado.getBairro(), "Bairro avião");
        assertEquals(enderecoRecuperado.getNumero(), "159");
        assertEquals(enderecoRecuperado.getComplemento(), "Complemento azul");
    }

    @Test
    public void testAtualizarUmEndereco() throws Exception {
        Estado estado = new Estado("PR", "Paraná");
        estadoRepository.save(estado);

        Cidade maringa = new Cidade(estado.getId(), "Maringá");
        cidadeRepository.save(maringa);

        Cep cep = new Cep(87140000, "Cep cepado");
        cepRepository.save(cep);

        Endereco endereco = new Endereco("rua", "bairro", "123", maringa, cep);
        enderecoRepository.save(endereco);

        String uri = String.format("/enderecos/%s", endereco.getId().toString());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rua", "rua alterada");
        jsonObject.put("bairro", "bairro alterado");
        jsonObject.put("numero", "n alt");
        jsonObject.put("complemento", "complemento alterado");

        MockHttpServletRequestBuilder request = put(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Endereco enderecoRecuperado = enderecoRepository.findOne(endereco.getId());
        assertEquals(enderecoRecuperado.getRua(), "rua alterada");
        assertEquals(enderecoRecuperado.getBairro(), "bairro alterado");
        assertEquals(enderecoRecuperado.getNumero(), "n alt");
        assertEquals(enderecoRecuperado.getComplemento(), "complemento alterado");
    }

    @Test
    public void testRecuperarEnderecoPeloId() throws Exception {
        Estado estado = new Estado("PR", "Paraná");
        estadoRepository.save(estado);

        Cidade maringa = new Cidade(estado.getId(), "Maringá");
        cidadeRepository.save(maringa);

        Cep cep = new Cep(87140000, "Cep cepado");
        cepRepository.save(cep);

        Endereco endereco = new Endereco("rua", "bairro", "123", maringa, cep);
        enderecoRepository.save(endereco);

        String uri = String.format("/enderecos/%s", endereco.getId().toString());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rua").value("rua"))
                .andExpect(jsonPath("$.bairro").value("bairro"))
                .andExpect(jsonPath("$.numero").value("123"))
                .andExpect(jsonPath("$.cidade.id").value(maringa.toString()))
                .andExpect(jsonPath("$.cep.id").value(cep.toString()));
    }


    @Test
    public void testApagarUmEndereco() throws Exception {
        Estado estado = new Estado("PR", "Paraná");
        estadoRepository.save(estado);

        Cidade maringa = new Cidade(estado.getId(), "Maringá");
        cidadeRepository.save(maringa);

        Cep cep = new Cep(87140000, "Cep cepado");
        cepRepository.save(cep);

        Endereco endereco = new Endereco("rua", "bairro", "123", maringa, cep);
        enderecoRepository.save(endereco);

        String uri = String.format("/enderecos/%s", endereco.getId().toString());

        mockMvc.perform(delete(uri))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

        Endereco recuperado = enderecoRepository.findOne(endereco.getId());

        assertNull(recuperado);

    }
}
