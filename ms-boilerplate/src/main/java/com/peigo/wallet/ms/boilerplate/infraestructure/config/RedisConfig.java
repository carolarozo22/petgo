package com.peigo.wallet.ms.boilerplate.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host:}")
    private String endpoint;

    @Value("${spring.redis.port:}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){

        return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration(){

        return new RedisStandaloneConfiguration(endpoint, port);
    }

    private JedisClientConfiguration jedisClientConfiguration(){

        return JedisClientConfiguration
                .builder()
                .readTimeout(Duration.ofMillis(0))
                .connectTimeout(Duration.ofMillis(0))
                .build();
    }
}
