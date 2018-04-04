package com.time02escoladeti.back;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("back.time02escoladeti.com")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .consumes(Collections.singleton("application/json"))
                .produces(Collections.singleton("application/json"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Back End REST API",
                "Back End Time 02 Escola de TI",
                "v1.0.0",
                "http://back.time02escoladeti.com",
                new Contact("Ilio adriano de Oliveira Junior", "http://back.time02escoladeti.com", "ilioadriano@live.com"),
                "MIT",
                "http://back.time02escoladeti.com",
                Collections.emptyList());
    }
}
