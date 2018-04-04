package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.categoria.Categoria;
import com.time02escoladeti.back.categoria.CategoriaController;
import com.time02escoladeti.back.categoria.CategoriaId;
import com.time02escoladeti.back.categoria.CategoriaRepository;
import com.time02escoladeti.back.foto.FotoId;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoriaControllerTest extends BackApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private CategoriaController categoriaController;

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "categoria");
    }

    @Test
    public void testCadastrarUmaCategoria() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Novo nome");
        jsonObject.put("descricao", "Nova descrição");
        jsonObject.put("ativo", true);

        MockHttpServletRequestBuilder request = post("/categorias")
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
        UUID entidadeId = UUID.fromString(objectRetorno.getString("idGerado"));

        Categoria categoriaTest = categoriaRepository.findOne(new CategoriaId(entidadeId.toString()));

        assertTrue(categoriaTest.getNome().equals("Novo nome"));
        assertTrue(categoriaTest.getDescricao().equals("Nova descrição"));
        assertTrue(categoriaTest.getAtivo().equals(Boolean.TRUE));
    }

    @Test
    public void testObterCategoriaInativa() throws Exception {
        Categoria categoria = new Categoria("Inativa");
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void testObterCategoriaPorId() throws Exception {
        Categoria categoria = new Categoria("Teste");
        categoria.setDescricao("Descrição");
        categoriaRepository.save(categoria);

        String url = String.format("/categorias/%s", categoria.getId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.descricao").value("Descrição"))
                .andExpect(jsonPath("$.ativo").value("true"));
    }

    @Test
    public void testObterListaDeCategorias() throws Exception {
        categoriaRepository.save(new Categoria("Test 01"));
        categoriaRepository.save(new Categoria("Test 02"));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Test 01"))
                .andExpect(jsonPath("$[0].descricao").isEmpty())
                .andExpect(jsonPath("$[1].nome").value("Test 02"))
                .andExpect(jsonPath("$[1].descricao").isEmpty());
    }

    @Test
    public void testEnviarUmaImagem() throws Exception {
        // Criando entidade da categoria
        Categoria categoria = new Categoria("Agrícola");
        categoriaRepository.save(categoria);

        // Configurando meu request
        Path path = Paths.get("mock/sports.jpeg");
        String url = String.format("/categorias/%s", categoria.getId().toString());
        MockMultipartFile requestFile = new MockMultipartFile("foto", "sports.jpeg", "image/jpeg", Files.readAllBytes(path));
        MockMultipartHttpServletRequestBuilder request = fileUpload(url).file(requestFile);

        // Assertando
        String jsonRetorno = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idGerado").isNotEmpty())
                .andExpect(jsonPath("$.idGerado").isString())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Validar se é um UUID
        JSONObject objectRetorno = new JSONObject(jsonRetorno);
        UUID entidadeId = UUID.fromString(objectRetorno.getString("idGerado"));

        FotoId fotoId = new FotoId(entidadeId.toString());

        Categoria categoriaRecuperada = categoriaRepository.findOne(categoria.getId());
        assertEquals(categoriaRecuperada.getIdFoto(), fotoId);
    }

    @Test
    public void testEditarUmaCategoria() throws Exception {
        Categoria categoria = new Categoria("Teste");
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Novo nome");
        jsonObject.put("descricao", "Nova descrição");
        jsonObject.put("ativo", true);

        String url = String.format("/categorias/%s", categoria.getId().toString());

        MockHttpServletRequestBuilder request = put(url)
                .contentType("application/json")
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        Categoria categoriaTest = categoriaRepository.findOne(categoria.getId());

        assertTrue(categoriaTest.getNome().equals("Novo nome"));
        assertTrue(categoriaTest.getDescricao().equals("Nova descrição"));
        assertTrue(categoriaTest.getAtivo().equals(Boolean.TRUE));
    }

    @Test
    public void testApagarUmaCategoria() throws Exception {
        Categoria categoria = new Categoria("teste");
        categoriaRepository.save(categoria);

        String url = String.format("/categorias/%s", categoria.getId().toString());

        mockMvc.perform(delete(url))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$").doesNotExist());

        Categoria categoriaTest = categoriaRepository.findOne(categoria.getId());
        assertNull(categoriaTest);

    }
}
