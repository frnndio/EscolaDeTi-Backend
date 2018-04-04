package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.estado.Estado;
import com.time02escoladeti.back.estado.EstadoController;
import com.time02escoladeti.back.estado.EstadoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EstadoControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private EstadoController estadoController;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(estadoController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "estado");
    }

    @Test
    public void testRecuperarListaDeEstados() throws Exception {
        Estado parana = new Estado("PR", "Paraná");
        Estado saoPaulo = new Estado("SP", "São Paulo");

        estadoRepository.save(parana);
        estadoRepository.save(saoPaulo);

        mockMvc.perform(get("/estados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].sigla").value("PR"))
                .andExpect(jsonPath("$[0].nome").value("Paraná"))
                .andExpect(jsonPath("$[1].sigla").value("SP"))
                .andExpect(jsonPath("$[1].nome").value("São Paulo"));
    }

    @Test
    public void testRecuperarUmEstado() throws Exception {
        Estado parana = new Estado("PR", "Paraná");
        estadoRepository.save(parana);

        String url = String.format("/estados/%s", parana.getId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sigla").value("PR"))
                .andExpect(jsonPath("$.nome").value("Paraná"));
    }
}
