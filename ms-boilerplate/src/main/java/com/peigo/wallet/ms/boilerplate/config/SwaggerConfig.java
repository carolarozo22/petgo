package com.peigo.wallet.ms.boilerplate.config;

import com.peigo.wallet.ms.boilerplate.constants.SpringDocConstant;
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
                .title(SpringDocConstant.CONFIG_TITLE)
                .description(SpringDocConstant.CONFIG_DESCRIPTION)
                .version(SpringDocConstant.CONFIG_VERSION)
                .termsOfService(SpringDocConstant.CONFIG_TERMS)
                .contact(new Contact()
                        .email(SpringDocConstant.CONFIG_EMAIL)
                        .url(SpringDocConstant.CONFIG_CONTACT_URL)
                        .name(SpringDocConstant.CONFIG_CONTACT_NAME))
                .license(new License()
                        .name(SpringDocConstant.CONFIG_LICENSE_NAME)
                        .url(SpringDocConstant.CONFIG_LICENSE_URL));
    }
}
