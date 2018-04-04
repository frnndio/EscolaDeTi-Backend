package com.time02escoladeti.back.Controllers;

import com.time02escoladeti.back.BackApplicationTests;
import com.time02escoladeti.back.foto.Foto;
import com.time02escoladeti.back.foto.FotoController;
import com.time02escoladeti.back.foto.FotoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FotoControllerTest extends BackApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private FotoController fotoController;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${back-end.application.files-url}")
    private String urlServidor;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fotoController).build();
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "foto");
    }

    @Test
    public void testRecuperarUmaFoto() throws Exception {
        Foto foto = new Foto("nome.jpg");
        fotoRepository.save(foto);

        String uri = String.format("/fotos/%s", foto.getId());
        String urlCompleta = String.format("%s%s", urlServidor, foto.getNomeFoto());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.urlFoto").value(urlCompleta));
    }
}
