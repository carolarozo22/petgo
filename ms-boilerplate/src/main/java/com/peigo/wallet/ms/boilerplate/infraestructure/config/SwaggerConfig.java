package com.peigo.wallet.ms.boilerplate.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(information());
    }

    private Info information(){
        return new Info()
                .title("BoilerPlate")
                .description("Project with base structure for microservices definition")
                .version("0.0.1")
                .contact(new Contact()
                        .email("peigo@globant.com")
                        .url("https://www.globant.com")
                        .name("Peigo Team"))
                .license(new License()
                        .name("GPL")
                        .url("https://es.wikipedia.org/wiki/GNU_General_Public_License"));
    }
}
