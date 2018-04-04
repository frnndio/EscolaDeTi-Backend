package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.cidade.Cidade;
import com.time02escoladeti.back.cidade.CidadeController;
import com.time02escoladeti.back.cidade.CidadeRepository;
import com.time02escoladeti.back.estado.Estado;
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
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CidadeControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private CidadeController cidadeController;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cidadeController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "cidade", "estado");
    }

    @Test
    public void testRecuperarCidadesPeloEstado() throws Exception {
        Estado estado = new Estado("PR", "Paraná");

        estadoRepository.save(estado);

        Cidade maringa = new Cidade(estado.getId(), "Maringá");
        Cidade paicandu = new Cidade(estado.getId(), "Paiçandu");
        cidadeRepository.save(maringa);
        cidadeRepository.save(paicandu);

        String url = String.format("/cidades/estado/%s", estado.getId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("Maringá"))
                .andExpect(jsonPath("$[0].idEstado").value(estado.getId().toString()))
                .andExpect(jsonPath("$[1].nome").value("Paiçandu"))
                .andExpect(jsonPath("$[1].idEstado").value(estado.getId().toString()));
    }

    @Test
    public void testRecuperarCidadePeloId() throws Exception {
        Estado estado = new Estado("PR", "Paraná");
        estadoRepository.save(estado);

        Cidade maringa = new Cidade(estado.getId(), "Maringá");
        cidadeRepository.save(maringa);

        String url = String.format("/cidades/%s", maringa.getId().toString());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maringá"))
                .andExpect(jsonPath("$.idEstado").value(estado.getId().toString()));
    }

    @Test
    public void testeHashCode() throws Exception {
        Estado estado = new Estado("PR", "Paraná");
        estadoRepository.save(estado);

        Cidade c1 = new Cidade(estado.getId(), "Maringá");
        Cidade c2 = new Cidade(estado.getId(), "Maringá");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

}
