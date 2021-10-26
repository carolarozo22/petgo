package com.peigo.wallet.ms.boilerplate.infraestructure.config;

import com.github.damianwajser.filter.StatsFilter;
import com.github.damianwajser.idempotency.configuration.IdempotencyEndpoints;
import com.peigo.wallet.ms.boilerplate.infraestructure.filter.IdempotencyKeyGeneratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class IdempotencyConfig {

    @Bean
    public IdempotencyEndpoints idempotencyEndpoints(){
        IdempotencyEndpoints idempotencyEndpoints = new IdempotencyEndpoints();
        idempotencyEndpoints.addIdempotencyEndpoint("/api/boilerplate/getUsers", new IdempotencyKeyGeneratorFilter(), HttpMethod.GET);
        return idempotencyEndpoints;
    }
}
