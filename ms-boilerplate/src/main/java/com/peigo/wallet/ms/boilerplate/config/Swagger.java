package com.peigo.wallet.ms.boilerplate.config;

import com.peigo.wallet.ms.boilerplate.constants.SpringDoc;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(information());
    }

    private Info information(){
        return new Info()
                .title(SpringDoc.CONFIG_TITLE)
                .description(SpringDoc.CONFIG_DESCRIPTION)
                .version(SpringDoc.CONFIG_VERSION)
                .termsOfService(SpringDoc.CONFIG_TERMS)
                .contact(new Contact()
                        .email(SpringDoc.CONFIG_EMAIL)
                        .url(SpringDoc.CONFIG_CONTACT_URL)
                        .name(SpringDoc.CONFIG_CONTACT_NAME))
                .license(new License()
                        .name(SpringDoc.CONFIG_LICENSE_NAME)
                        .url(SpringDoc.CONFIG_LICENSE_URL));
    }
}
