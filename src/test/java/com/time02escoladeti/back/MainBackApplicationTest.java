package com.time02escoladeti.back;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class MainBackApplicationTest extends BackApplicationTests {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;
    @Value("${spring.jpa.database-platform}")
    private String dbDialect;

    @Test
    public void testMain() throws Exception {
        BackApplication.main(new String[]{
                "--spring.datasource.driver-class-name=" + dbDriver,
                "--spring.datasource.url=" + dbUrl,
                "--spring.jpa.database-platform=" + dbDialect,
                "--server.port=1234"
        });
    }
}
