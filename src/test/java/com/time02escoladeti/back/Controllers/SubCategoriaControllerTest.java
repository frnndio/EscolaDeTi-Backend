package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.categoria.Categoria;
import com.time02escoladeti.back.categoria.CategoriaRepository;
import com.time02escoladeti.back.subcategoria.SubCategoria;
import com.time02escoladeti.back.subcategoria.SubCategoriaController;
import com.time02escoladeti.back.subcategoria.SubCategoriaId;
import com.time02escoladeti.back.subcategoria.SubCategoriaRepository;
import org.hamcrest.Matchers;
import org.json.JSONException;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubCategoriaControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private SubCategoriaController subCategoriaController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subCategoriaController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "categoria", "sub_categoria");
    }

    @Test
    public void testGetAllSemSubCategoriaCadastrada() throws Exception {
        mockMvc.perform(get("/subcategorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testObterListaDeSubCategorias() throws Exception {
        Categoria categoria = new Categoria("categoria");
        categoriaRepository.save(categoria);

        SubCategoria s1 = new SubCategoria(categoria.getId(), "Sub name 01");
        s1.setDescricao("Sub desc 01");

        SubCategoria s2 = new SubCategoria(categoria.getId(), "Sub name 02");
        s2.setDescricao("Sub desc 02");

        SubCategoria s3 = new SubCategoria(categoria.getId(), "Sub name 03");
        s3.setDescricao("Sub desc 03");
        s3.setAtivo(false);

        subCategoriaRepository.save(s1);
        subCategoriaRepository.save(s2);
        subCategoriaRepository.save(s3);

        mockMvc.perform(get("/subcategorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Sub name 01"))
                .andExpect(jsonPath("$[0].descricao").value("Sub desc 01"))
                .andExpect(jsonPath("$[0].idCategoria").value(categoria.getId().toString()))
                .andExpect(jsonPath("$[1].nome").value("Sub name 02"))
                .andExpect(jsonPath("$[1].descricao").value("Sub desc 02"))
                .andExpect(jsonPath("$[1].idCategoria").value(categoria.getId().toString()));
    }

    @Test
    public void testObterUmaSubCategoria() throws Exception {

        Categoria categoria = new Categoria("categoria");
        categoriaRepository.save(categoria);

        SubCategoria s1 = new SubCategoria(categoria.getId(), "Sub name 01");
        s1.setDescricao("Sub desc 01");

        subCategoriaRepository.save(s1);

        String url = String.format("/subcategorias/%s", s1.getId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Sub name 01"))
                .andExpect(jsonPath("$.descricao").value("Sub desc 01"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.idCategoria").value(categoria.getId().toString()));

    }

    @Test
    public void testObterUmaSubCategoriaQueNaoExiste() throws Exception {

        Categoria categoria = new Categoria("categoria");
        categoriaRepository.save(categoria);

        SubCategoria s1 = new SubCategoria(categoria.getId(), "Sub name 01");
        s1.setDescricao("Sub desc 01");
        s1.setAtivo(false);

        subCategoriaRepository.save(s1);

        String url = String.format("/subcategorias/%s", s1.getId());

        mockMvc.perform(get(url))
                .andExpect(status().is(404));
    }

    @Test
    public void testCadastrarNovaSubCategoria() throws Exception {
        Categoria categoria = new Categoria("categoria");
        categoriaRepository.save(categoria);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Teste 01");
        jsonObject.put("descricao", "Teste 01 desc");
        jsonObject.put("idCategoria", categoria.getId().toString());

        MockHttpServletRequestBuilder request = post("/subcategorias")
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

        SubCategoriaId subCategoriaId = new SubCategoriaId(uuidGerado.toString());

        SubCategoria one = subCategoriaRepository.findOne(subCategoriaId);

        assertEquals(one.getNome(), "Teste 01");
        assertEquals(one.getDescricao(), "Teste 01 desc");
        assertEquals(one.getAtivo(), Boolean.TRUE);
        assertEquals(one.getIdCategoria().toString(), categoria.getId().toString());
    }

    @Test
    public void testObterListaDeSubCategoriasPeloIdDaCategoria() throws Exception {
        Categoria categoria = new Categoria("categoria");
        categoriaRepository.save(categoria);

        SubCategoria s1 = new SubCategoria(categoria.getId(), "Sub name 01");
        s1.setDescricao("Sub desc 01");

        SubCategoria s2 = new SubCategoria(categoria.getId(), "Sub name 02");
        s2.setDescricao("Sub desc 02");

        SubCategoria s3 = new SubCategoria(categoria.getId(), "Sub name 03");
        s3.setDescricao("Sub desc 03");
        s3.setAtivo(false);

        subCategoriaRepository.save(s1);
        subCategoriaRepository.save(s2);
        subCategoriaRepository.save(s3);

        String uri = String.format("/subcategorias/categoria/%s", categoria.getId().toString());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Sub name 01"))
                .andExpect(jsonPath("$[0].descricao").value("Sub desc 01"))
                .andExpect(jsonPath("$[0].idCategoria").value(categoria.getId().toString()))
                .andExpect(jsonPath("$[1].nome").value("Sub name 02"))
                .andExpect(jsonPath("$[1].descricao").value("Sub desc 02"))
                .andExpect(jsonPath("$[1].idCategoria").value(categoria.getId().toString()));
    }

    @Test
    public void testAtualizarUmaSubCategoria() throws Exception {
        Categoria categoria = new Categoria("Categoria");

        categoriaRepository.save(categoria);

        SubCategoria s1 = new SubCategoria(categoria.getId(), "SubCategoria");
        s1.setDescricao("aaaa");
        s1.setAtivo(false);

        subCategoriaRepository.save(s1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", "Alterado nome");
        jsonObject.put("descricao", "Alterado descrição");
        jsonObject.put("ativo", Boolean.TRUE);

        String uri = String.format("/subcategorias/%s", s1.getId());

        MockHttpServletRequestBuilder request = put(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonObject.toString());

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        SubCategoria recuperado = subCategoriaRepository.findOne(s1.getId());

        assertEquals(recuperado.getNome(), "Alterado nome");
        assertEquals(recuperado.getDescricao(), "Alterado descrição");
        assertEquals(recuperado.getAtivo(), Boolean.TRUE);
    }

    @Test
    public void testApagarSubCategoria() throws Exception {
        Categoria categoria = new Categoria("Categoria");
        categoriaRepository.save(categoria);

        SubCategoria s1 = new SubCategoria(categoria.getId(), "SubCategoria");
        subCategoriaRepository.save(s1);

        String uri = String.format("/subcategorias/%s", s1.getId());

        mockMvc.perform(delete(uri))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$").doesNotExist());

        SubCategoria recuperado = subCategoriaRepository.findOne(s1.getId());
        assertNull(recuperado);
    }

}
