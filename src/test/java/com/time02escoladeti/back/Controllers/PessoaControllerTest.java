package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.Enums.InformaçãoTributária;
import com.time02escoladeti.back.Enums.Sexo;
import com.time02escoladeti.back.pessoa.*;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PessoaControllerTest extends BackApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private PessoaController pessoaController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "pessoa", "pessoa_fisica", "pessoa_juridica");
    }

    @Test
    public void testCadastrarPessoaFisicaWeb() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = dateFormat.parse("1996-11-22T00:00:00.000");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Ilio Adriano de Oliveira Junior");
        jsonObject.put("email", "ilioadriano@live.com");
        jsonObject.put("celular", 44998762515L);
        jsonObject.put("telefone", 4432444745L);
        jsonObject.put("cpf", 10498391965L);
        jsonObject.put("rg", 123456789);
        jsonObject.put("sexo", "MASCULINO");
        jsonObject.put("dataNascimento", dateFormat.format(date));

        MockHttpServletRequestBuilder request = post("/pessoas/fisica")
                .contentType("application/json")
                .content(jsonObject.toString());

        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.idGerado").isString())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID uuidGerado = UUID.fromString(objectRetorno.getString("idGerado"));

        PessoaId idPessoa = new PessoaId(uuidGerado.toString());

        PessoaFisica pessoa = (PessoaFisica) pessoaRepository.findOne(idPessoa);

        Assert.assertEquals(pessoa.getNome(), "Ilio Adriano de Oliveira Junior");
        Assert.assertEquals(pessoa.getEmail(), "ilioadriano@live.com");
        assertTrue(pessoa.getTelefone().equals(4432444745L));
        assertTrue(pessoa.getCelular().equals(44998762515L));
        assertTrue(pessoa.getCPF().equals(10498391965L));
        assertTrue(pessoa.getRG().equals(123456789));
        assertTrue(pessoa.getSexo().equals(Sexo.MASCULINO));
        assertEquals(date, pessoa.getDataNascimento());
    }

    @Test
    public void testCadastrarPessoaFisicaMobile() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Ilio Adriano de Oliveira Junior");
        jsonObject.put("email", "ilioadriano@live.com");

        MockHttpServletRequestBuilder request = post("/pessoas/fisica")
                .contentType("application/json")
                .content(jsonObject.toString());

        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.idGerado").isString())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID uuidGerado = UUID.fromString(objectRetorno.getString("idGerado"));

        PessoaId idPessoa = new PessoaId(uuidGerado.toString());

        PessoaFisica pessoa = (PessoaFisica) pessoaRepository.findOne(idPessoa);

        Assert.assertEquals(pessoa.getNome(), "Ilio Adriano de Oliveira Junior");
        Assert.assertEquals(pessoa.getEmail(), "ilioadriano@live.com");
    }

    @Test
    public void testCadastrarPessoaJuridicaWeb() throws Exception {


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Ilio Adriano de Oliveira Junior");
        jsonObject.put("email", "ilioadriano@live.com");
        jsonObject.put("telefone", 4432444745L);
        jsonObject.put("celular", 44998762515L);
        jsonObject.put("cnpj", 10785790000111L);
        jsonObject.put("inscricaoEstadual", 123456789);
        jsonObject.put("nomeResponsavel", "Ilio");
        jsonObject.put("informacaoTributaria", "CONTRIBUINTE");

        MockHttpServletRequestBuilder request = post("/pessoas/juridica")
                .contentType("application/json")
                .content(jsonObject.toString());

        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.idGerado").isString())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andReturn()
                .getResponse().getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID uuidGerado = UUID.fromString(objectRetorno.getString("idGerado"));

        PessoaId idPessoa = new PessoaId(uuidGerado.toString());

        PessoaJuridica pessoa = (PessoaJuridica) pessoaRepository.findOne(idPessoa);

        Assert.assertEquals(pessoa.getNome(), "Ilio Adriano de Oliveira Junior");
        Assert.assertEquals(pessoa.getEmail(), "ilioadriano@live.com");
        assertTrue(pessoa.getTelefone().equals(4432444745L));
        assertTrue(pessoa.getCelular().equals(44998762515L));
        assertTrue(pessoa.getCNPJ().equals(10785790000111L));
        assertTrue(pessoa.getInscricaoEstadual().equals(123456789));
        assertTrue(pessoa.getNomeResponsavel().equals("Ilio"));
        assertTrue(pessoa.getInformacaoTributaria().equals(InformaçãoTributária.CONTRIBUINTE));

    }

    @Test
    public void testCadastrarPessoaJuridicaMobile() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Ilio Adriano de Oliveira Junior");
        jsonObject.put("email", "ilioadriano@live.com");

        MockHttpServletRequestBuilder request = post("/pessoas/juridica")
                .contentType("application/json")
                .content(jsonObject.toString());

        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.idGerado").isString())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID uuidGerado = UUID.fromString(objectRetorno.getString("idGerado"));

        PessoaId idPessoa = new PessoaId(uuidGerado.toString());

        PessoaJuridica pessoa = (PessoaJuridica) pessoaRepository.findOne(idPessoa);

        Assert.assertEquals(pessoa.getNome(), "Ilio Adriano de Oliveira Junior");
        Assert.assertEquals(pessoa.getEmail(), "ilioadriano@live.com");
    }

    @Test
    public void testEditarPessoaFisica() throws Exception {
        PessoaFisica ilio = new PessoaFisica("Ilio", "ilioadriano@live.com");
        pessoaRepository.save(ilio);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Alterado");
        jsonObject.put("email", "email");
        jsonObject.put("telefone", 32444745);
        jsonObject.put("celular", 44998762515L);
        jsonObject.put("cpf", 10498391965L);
        jsonObject.put("rg", 123456);
        jsonObject.put("dataNascimento", "2000-01-01");
        jsonObject.put("sexo", "FEMININO");

        String url = String.format("/pessoas/fisica/%s", ilio.getId().toString());

        MockHttpServletRequestBuilder request = put(url)
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        PessoaFisica one = (PessoaFisica) pessoaRepository.findOne(ilio.getId());
        assertTrue(one.getNome().equals("Alterado"));
        assertTrue(one.getEmail().equals("email"));
        assertTrue(one.getTelefone().equals(32444745L));
        assertTrue(one.getCelular().equals(44998762515L));
        assertTrue(one.getCPF().equals(10498391965L));
        assertTrue(one.getRG().equals(123456));
        assertTrue(one.getSexo().equals(Sexo.FEMININO));
    }

    @Test
    public void testEditarPessoaJuridica() throws Exception {
        PessoaJuridica ilio = new PessoaJuridica("Ilio", "ilioadriano@live.com");
        pessoaRepository.save(ilio);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Alterado");
        jsonObject.put("email", "email");
        jsonObject.put("telefone", 32444745);
        jsonObject.put("celular", 44998762515L);
        jsonObject.put("cnpj", 10498391965L);
        jsonObject.put("inscricaoEstadual", 123456);
        jsonObject.put("nomeResponsavel", "responsavel");
        jsonObject.put("informacaoTributaria", "CONTRIBUINTE");

        String url = String.format("/pessoas/juridica/%s", ilio.getId().toString());

        MockHttpServletRequestBuilder request = put(url)
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        PessoaJuridica one = (PessoaJuridica) pessoaRepository.findOne(ilio.getId());
        assertTrue(one.getNome().equals("Alterado"));
        assertTrue(one.getEmail().equals("email"));
        assertTrue(one.getTelefone().equals(32444745L));
        assertTrue(one.getCelular().equals(44998762515L));
        assertTrue(one.getCNPJ().equals(10498391965L));
        assertTrue(one.getInscricaoEstadual().equals(123456));
        assertTrue(one.getNomeResponsavel().equals("responsavel"));
        assertTrue(one.getInformacaoTributaria().equals(InformaçãoTributária.CONTRIBUINTE));
    }

    @Test
    public void testEqualsPessoaFisica() throws Exception {
        Date date = Calendar.getInstance().getTime();

        PessoaFisica p1 = new PessoaFisica("aaa", "aaa", 10498391965L, date);
        PessoaFisica p2 = new PessoaFisica("aaa", "aaa", 10498391965L, date);

        assertTrue(p1.equals(p2));
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testRecuperarPessoaFisica() throws Exception {
        Date date = Calendar.getInstance().getTime();
        PessoaFisica pessoaFisica = new PessoaFisica("aaa", "aaa@aaa.com", 10498391965L, date);
        pessoaFisica.setRG(123456);
        pessoaFisica.setSexo(Sexo.FEMININO);

        pessoaRepository.save(pessoaFisica);

        String uri = String.format("/pessoas/%s", pessoaFisica.getId().toString());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("aaa"))
                .andExpect(jsonPath("$.email").value("aaa@aaa.com"))
                .andExpect(jsonPath("$.rg").value("123456"))
                .andExpect(jsonPath("$.sexo").value("FEMININO"));
    }

    @Test
    public void testRecuperarPessoaJuridica() throws Exception {
        PessoaJuridica pessoaJuridica = new PessoaJuridica("aaa", "aaa@aaa.com", 10785790000111L, "Ilio");
        pessoaJuridica.setInscricaoEstadual(123456);
        pessoaJuridica.setInformacaoTributaria(InformaçãoTributária.ISENTO);

        pessoaRepository.save(pessoaJuridica);

        String uri = String.format("/pessoas/%s", pessoaJuridica.getId().toString());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("aaa"))
                .andExpect(jsonPath("$.email").value("aaa@aaa.com"))
                .andExpect(jsonPath("$.inscricaoEstadual").value("123456"))
                .andExpect(jsonPath("$.informacaoTributaria").value("ISENTO"));
    }

    @Test
    public void testEqualsPessoaFisicaTelefoneDiferente() throws Exception {
        Date date = Calendar.getInstance().getTime();

        PessoaFisica p1 = new PessoaFisica("aaa", "aaa", 10498391965L, date);
        PessoaFisica p2 = new PessoaFisica("aaa", "aaa", 10498391965L, date);
        p2.setTelefone(123L);

        assertFalse(p1.equals(p2));
        assertFalse(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testEqualsPessoaJuridica() throws Exception {
        PessoaJuridica p1 = new PessoaJuridica("aaa", "aaa", 10785790000111L, "aaa");
        p1.setInscricaoEstadual(123);
        p1.setTelefone(1234L);
        PessoaJuridica p2 = new PessoaJuridica("aaa", "aaa", 10785790000111L, "aaa");
        p2.setInscricaoEstadual(123);
        p2.setTelefone(1234L);

        assertTrue(p1.equals(p2));
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testEqualsPessoaJuridicaInscricaoDiferente() throws Exception {
        PessoaJuridica p1 = new PessoaJuridica("aaa", "aaa", 10785790000111L, "aaa");
        PessoaJuridica p2 = new PessoaJuridica("aaa", "aaa", 10785790000111L, "aaa");
        p2.setInscricaoEstadual(1234);

        assertFalse(p1.equals(p2));
        assertFalse(p1.hashCode() == p2.hashCode());
    }

    @Test
    public void testEqualsPessoaJuridicaResponsavelDiferente() throws Exception {
        PessoaJuridica p1 = new PessoaJuridica("aaa", "aaa", 10785790000111L, "aaa");
        PessoaJuridica p2 = new PessoaJuridica("aaa", "aaa", 10785790000111L, "aaa2");

        assertFalse(p1.equals(p2));
        assertFalse(p1.hashCode() == p2.hashCode());
    }
}
