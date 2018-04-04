package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.cep.Cep;
import com.time02escoladeti.back.cep.CepController;
import com.time02escoladeti.back.cep.CepRepository;
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

public class CepControllerTest extends BackApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private CepController cepController;

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cepController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "cep");
    }

    @Test
    public void testGetAllCeps() throws Exception {

        cepRepository.save(new Cep(87140000, "Teste 01"));
        cepRepository.save(new Cep(87140080, "Teste 02"));

        mockMvc.perform(get("/cep"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cep").value("87140000"))
                .andExpect(jsonPath("$[0].descricao").value("Teste 01"))
                .andExpect(jsonPath("$[1].cep").value("87140080"))
                .andExpect(jsonPath("$[1].descricao").value("Teste 02"));

    }

    @Test
    public void testGetCepById() throws Exception {

        Cep cep = new Cep(87140000, "Teste 01");
        cepRepository.save(cep);

        String uri = String.format("/cep/%s", cep.getId().toString());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("87140000"))
                .andExpect(jsonPath("$.descricao").value("Teste 01"));
    }

    @Test
    public void testHashCode() throws Exception {
        Cep c1 = new Cep(87140000, "Teste");
        Cep c2 = new Cep(87140000, "Teste");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

}
